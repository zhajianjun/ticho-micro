package com.ticho.uaa.security.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，主要登录需要额外的校验
 *
 * @author AdoroTutto
 * @date 2020-09-13 15:35
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private static final String OAUTH_TOKEN_URL = "/**/oauth/token";


    /**
     * 请求url接口
     */
    //private final RequestMatcher requestMatcher;

    // @formatter:off

    public LoginInterceptor() {
        //this.requestMatcher = new OrRequestMatcher(new AntPathRequestMatcher(OAUTH_TOKEN_URL, "POST"));
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)  {
        log.debug("登录拦截");
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
    }
}
