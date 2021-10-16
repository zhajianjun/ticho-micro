package com.ticho.mvc.config;

import com.ticho.mvc.exception.BaseResponseHandle;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 *
 *
 * @author AdoroTutto
 * @date 2021-10-16 22:44
 */
@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 10)
public class BaseMvcConfig implements WebMvcConfigurer {

    /**
     * 主要处理  ResponseHandle#beforeBodyWrite中返回String,使用 StringHttpMessageConverter，但是最终返回的是Result对象，导致
     * StringHttpMessageConverter转换异常，所以去除StringHttpMessageConverter，并添加MappingJackson2HttpMessageConverter作为默认的转换器
     * @see BaseResponseHandle#beforeBodyWrite(java.lang.Object, org.springframework.core.MethodParameter, org.springframework.http.MediaType, java.lang.Class, org.springframework.http.server.ServerHttpRequest, org.springframework.http.server.ServerHttpResponse)
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
        converters.removeIf(httpMessageConverter -> httpMessageConverter.getClass() == StringHttpMessageConverter.class);
    }

}
