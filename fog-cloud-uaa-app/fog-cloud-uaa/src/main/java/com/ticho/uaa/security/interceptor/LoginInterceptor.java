package com.ticho.uaa.security.interceptor;

import com.ticho.core.mvc.exception.ServiceException;
import com.ticho.core.mvc.util.JsonUtils;
import com.ticho.core.mvc.view.Result;
import com.ticho.uaa.security.service.LoginVerifyContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录拦截器，主要登录需要额外的校验
 *
 * @author AdoroTutto
 * @date 2020-09-13 15:35
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private static final String OAUTH_TOKEN_URL = "/**/oauth/token";
    private static final String GRANT_TYPE = "grant_type";
    private static final String PASSWORD_GRANT_TYPE = "password";


    /**
     * 请求url接口
     */
    private final RequestMatcher requestMatcher;

    // @formatter:off

    @Autowired
    private LoginVerifyContext loginVerifyContext;

    public LoginInterceptor() {
        this.requestMatcher = new OrRequestMatcher(new AntPathRequestMatcher(OAUTH_TOKEN_URL, "POST"));
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        // 只对密码模式进行拦截
        if(!(requestMatcher.matches(request) && PASSWORD_GRANT_TYPE.equals(request.getParameter(GRANT_TYPE)))){
            return true;
        }
        // 登录策略执行 前置执行
        try {
            log.debug("登录拦截");
            loginVerifyContext.verify();
            return true;
        } catch (Exception e) {
            response.setContentType(""
                    + "");
            // 异常返回输出流
            PrintWriter out = response.getWriter();
            Result<String> view;
            // 主动异常抛出 和 系统异常抛出 的不同处理
            if (e instanceof ServiceException) {
                ServiceException serviceException = (ServiceException) e;
                view = new Result<>();
                view.code(serviceException.getCode());
                view.msg(serviceException.getMsg());
                view.data(request.getRequestURI());
            } else {
                view = Result.fail(request.getRequestURI());
            }
            response.setStatus(HttpServletResponse.SC_OK);
            log.error(e.getMessage(), e);
            out.write(JsonUtils.toJsonString(view));
            log.error("登录失败",e);
            out.close();
            return false;
        }
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
    }
}
