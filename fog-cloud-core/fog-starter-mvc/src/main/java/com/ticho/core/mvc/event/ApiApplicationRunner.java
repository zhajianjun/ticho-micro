package com.ticho.core.mvc.event;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 获取程序中的api接口
 * 应用程序运行器，应用启动时运行
 *
 * @author AdoroTutto
 * @date 2021-10-17 10:35
 */
@SuppressWarnings("ALL")
@Slf4j
@Component
public class ApiApplicationRunner implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    @Async
    public void run(ApplicationArguments args) {
        api();
    }

    private void api() {
        Environment env = applicationContext.getEnvironment();
        // 所有接口映射
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Map<String, String>> list = new ArrayList<>();
        // 获取微服务模块名称
        String microService = env.getProperty("spring.application.name", "application");
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            if (method.getMethodAnnotation(ApiIgnore.class) != null) {
                // 忽略的接口不扫描
                continue;
            }
            // 请求路径
            PatternsRequestCondition p = info.getPatternsCondition();
            if (p == null) {
                continue;
            }
            String urls = getUrls(p.getPatterns());
            //if (isIgnore(urls)) {
            //    continue;
            //}
            Set<MediaType> mediaTypeSet = info.getProducesCondition().getProducibleMediaTypes();
            for (MethodParameter params : method.getMethodParameters()) {
                if (params.hasParameterAnnotation(RequestBody.class)) {
                    mediaTypeSet.add(MediaType.APPLICATION_JSON);
                    break;
                }
            }
            String mediaTypes = getMediaTypes(mediaTypeSet);
            // 请求类型
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            String methods = getMethods(methodsCondition.getMethods());
            Map<String, String> api = new HashMap<>(16);
            // 类名
            String className = method.getMethod().getDeclaringClass().getName();
            // 方法名
            String methodName = method.getMethod().getName();
            // md5码
            String md5 = DigestUtil.md5Hex(microService + urls);
            String name = "";
            String notes = "";
            String auth = "0";

            ApiOperation apiOperation = method.getMethodAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                name = apiOperation.value();
                notes = apiOperation.notes();
            }
            name = StrUtil.isEmpty(name) ? methodName : name;
            api.put("name", name);
            api.put("notes", notes);
            api.put("path", urls);
            api.put("code", md5);
            api.put("className", className);
            api.put("methodName", methodName);
            api.put("method", methods);
            api.put("serviceId", microService);
            api.put("contentType", mediaTypes);
            api.put("auth", auth);
            list.add(api);
        }
        Map<String, Object> res = new HashMap<>(4);
        res.put("serviceId", microService);
        res.put("size", list.size());
        res.put("list", list);
        log.info(res.toString());
    }

    private String getUrls(Set<String> urls) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String url : urls) {
            stringBuilder.append(url).append(",");
        }
        if (urls.size() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取媒体类型
     *
     * @param mediaTypes 类型SET集
     * @return String
     */
    private String getMediaTypes(Set<MediaType> mediaTypes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (MediaType mediaType : mediaTypes) {
            stringBuilder.append(mediaType.toString()).append(",");
        }
        if (mediaTypes.size() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取方法
     *
     * @param requestMethods 请求方法
     * @return String
     */
    private String getMethods(Set<RequestMethod> requestMethods) {
        StringBuilder stringBuilder = new StringBuilder();
        for (RequestMethod requestMethod : requestMethods) {
            stringBuilder.append(requestMethod.toString()).append(",");
        }
        if (requestMethods.size() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
