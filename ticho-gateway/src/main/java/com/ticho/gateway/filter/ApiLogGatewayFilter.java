package com.ticho.gateway.filter;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ticho.boot.json.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/**
 * api日志网关过滤
 *
 * @author zhajianjun
 * @date 2023-01-06 10:10
 */
@Slf4j
//@Component
public class ApiLogGatewayFilter implements WebFilter {

    private static final String UNKNOWN = "unknown";

    private final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    private static final String CONTENT_TYPE = "application/json";

    // 请求来源应用
    private static final String REQUEST_ORIGIN_APP = "Request-Origin-App";

    // 自定义请求头，转发之前删除自定义请求头
    private static final List<String> CUSTOM_HEADERS = Arrays.asList("sign", "timestamp", "random", REQUEST_ORIGIN_APP);

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 请求路径
        String requestPath = request.getPath().pathWithinApplication().value();

        // 获取路由信息
        Route route = getGatewayRoute(exchange);


        GatewayLog gatewayLog = new GatewayLog();
        gatewayLog.setOrigin(request.getHeaders().getFirst(REQUEST_ORIGIN_APP));
        gatewayLog.setSchema(request.getURI().getScheme());
        gatewayLog.setRequestMethod(request.getMethodValue());
        gatewayLog.setRequestPath(requestPath);
        String targetServer = Optional.ofNullable(route).map(Route::getUri).map(URI::toString).orElse(null);
        gatewayLog.setTargetServer(targetServer);
        gatewayLog.setStartTime(new Date().getTime());
        gatewayLog.setIp(getIp(request));
        gatewayLog.setRouteConfig(JSON.toJSONString(route));
        Map<String, Object> headers = new HashMap<>();
        for (String key : request.getHeaders().keySet()) {
            headers.put(key, request.getHeaders().getFirst(key));
        }
        gatewayLog.setHeaders(headers);

        MediaType mediaType = request.getHeaders().getContentType();
        if (mediaType != null) {
            gatewayLog.setRequestContentType(mediaType.toString());
        }
        if (mediaType != null && (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType) || MediaType.APPLICATION_JSON.isCompatibleWith(mediaType))) {
            return writeBodyLog(exchange, chain, gatewayLog);
        } else {
            return writeBasicLog(exchange, chain, gatewayLog);
        }
    }

    /**
     * 获取路由信息
     *
     * @param exchange 交换
     * @return {@link Route}
     */
    private Route getGatewayRoute(ServerWebExchange exchange) {
        return exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
    }


    private Mono<Void> writeBasicLog(ServerWebExchange exchange, WebFilterChain chain, GatewayLog gatewayLog) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        gatewayLog.setRequestBody(getUrlParamsByMap(queryParams));

        // 修改Header
        ServerHttpRequest mutableReq = exchange.getRequest().mutate().headers(httpHeaders -> {
            // 删除自定义header
            for (String customHeader : CUSTOM_HEADERS) {
                httpHeaders.remove(customHeader);
            }
        }).build();

        //获取响应体
        ServerHttpResponseDecorator decoratedResponse = recordResponseLog(exchange, gatewayLog);

        return chain.filter(exchange.mutate().request(mutableReq).response(decoratedResponse).build())
                .then(Mono.fromRunnable(() -> writeAccessLog(gatewayLog)));
    }


    /**
     * 解决 request body 只能读取一次问题，
     * 参考: org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory
     * @param exchange exchange
     * @param chain chain
     * @param gatewayLog gatewayLog
     * @return Mono<Void>
     */
    private Mono<Void> writeBodyLog(ServerWebExchange exchange, WebFilterChain chain, GatewayLog gatewayLog) {
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);

        Mono<Map> modifiedBody = serverRequest.bodyToMono(Map.class).flatMap(body -> {
            gatewayLog.setRequestBody(body);
            return Mono.just(body);
        });

        // 通过 BodyInserter 插入 body(支持修改body), 避免 request body 只能获取一次
        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, Map.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        // the new content type will be computed by bodyInserter
        // and then set in the request decorator
        headers.remove(HttpHeaders.CONTENT_LENGTH);

        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);

        return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
            // 重新封装请求
            ServerHttpRequest decoratedRequest = requestDecorate(exchange, headers, outputMessage);

            // 记录响应日志
            ServerHttpResponseDecorator decoratedResponse = recordResponseLog(exchange, gatewayLog);

            // 记录普通的
            return chain.filter(exchange.mutate().request(decoratedRequest).response(decoratedResponse).build())
                    .then(Mono.fromRunnable(() -> writeAccessLog(gatewayLog)));
        }));
    }


    /**
     * 打印日志
     * @param gatewayLog 网关日志
     */
    private void writeAccessLog(GatewayLog gatewayLog) {
        log.info(JsonUtil.toJsonString(gatewayLog));
    }


    /**
     * 请求装饰器，重新计算 headers
     *
     * @param exchange 交换
     * @param headers 头
     * @param outputMessage 输出消息
     * @return {@link ServerHttpRequestDecorator}
     */
    private ServerHttpRequestDecorator requestDecorate(ServerWebExchange exchange, HttpHeaders headers,
            CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            @NonNull
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }

                // 删除自定义header
                for (String customHeader : CUSTOM_HEADERS) {
                    headers.remove(customHeader);
                }

                return httpHeaders;
            }

            @Override
            @NonNull
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }


    /**
     * 记录响应日志
     * 通过 DataBufferFactory 解决响应体分段传输问题。
     */
    private ServerHttpResponseDecorator recordResponseLog(ServerWebExchange exchange, GatewayLog gatewayLog) {
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();

        return new ServerHttpResponseDecorator(response) {
            @Override
            @NonNull
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Date responseTime = new Date();
                    gatewayLog.setEndTime(responseTime.getTime());
                    // 计算执行时间
                    long executeTime = (responseTime.getTime() - gatewayLog.getStartTime());

                    gatewayLog.setExecuteTime(executeTime);
                    boolean success = Optional.ofNullable(response.getStatusCode())
                        .map(HttpStatus::value)
                        .map(x-> Objects.equals(x, 200))
                        .orElse(false);
                    gatewayLog.setStatus(success ? "成功" : "失败");

                    // 获取响应类型，如果是 json 就打印
                    String originalResponseContentType = exchange.getAttribute(ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);

                    if (Objects.equals(this.getStatusCode(), HttpStatus.OK) && StrUtil.isNotBlank(originalResponseContentType) && originalResponseContentType.contains(CONTENT_TYPE)) {

                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {

                            // 合并多个流集合，解决返回体分段传输
                            DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                            DataBuffer join = dataBufferFactory.join(dataBuffers);
                            byte[] content = new byte[join.readableByteCount()];
                            join.read(content);

                            // 释放掉内存
                            DataBufferUtils.release(join);
                            String responseResult = new String(content, StandardCharsets.UTF_8);

                            gatewayLog.setResponseData(JsonUtil.toMap(responseResult));

                            return bufferFactory.wrap(content);
                        }));
                    }
                }
                return super.writeWith(body);
            }
        };
    }


    /**
     * 将map参数转换成url参数
     *
     * @param map map
     * @return {@link String}
     */
    private String getUrlParamsByMap(MultiValueMap<String, String> map) {
        if (MapUtil.isEmpty(map)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue().get(0));
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StrUtil.subBefore(s, "&", true);
        }
        return s;
    }


    public String getIp(ServerHttpRequest request) {
        String ip = null;
        HttpHeaders headers = request.getHeaders();
        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = getHeader(headers, "X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = getHeader(headers, "Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = getHeader(headers, "WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = getHeader(headers, "HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = getHeader(headers, "X-Real-IP");
        }
        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }
        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            InetSocketAddress remoteAddress = request.getRemoteAddress();
            ip = Optional.ofNullable(remoteAddress).map(InetSocketAddress::getHostString).orElse(null);
        }
        return ip;
    }

    public String getHeader(HttpHeaders httpHeaders, String key) {
        if (Objects.isNull(httpHeaders)) {
            return null;
        }
        List<String> strings = httpHeaders.get(key);
        if (strings == null || strings.isEmpty()) {
            return null;
        }
        return strings.get(0);
    }

}
