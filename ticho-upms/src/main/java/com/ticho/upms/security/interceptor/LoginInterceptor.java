package com.ticho.upms.security.interceptor;

import com.ticho.upms.security.service.LoginHandleContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，主要登录需要额外的校验
 *
 * @author zhajianjun
 * @date 2020-09-13 15:35
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private static final String OAUTH_TOKEN_URL = "/**/oauth/**";

    /**
     * 请求url接口
     */
    private final RequestMatcher requestMatcher;

    // @formatter:off

    public LoginInterceptor() {
        this.requestMatcher = new OrRequestMatcher(new AntPathRequestMatcher(OAUTH_TOKEN_URL));
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)  {
        log.debug("登录拦截{}", request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) {
        log.debug("postHandle");
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        log.debug("afterCompletion");
        if(!(requestMatcher.matches(request))){
            return;
        }
        // 删除缓存数据
        LoginHandleContext.oauthClientThreadLocal.remove();
        LoginHandleContext.userDetailsThreadLocal.remove();
    }
}
