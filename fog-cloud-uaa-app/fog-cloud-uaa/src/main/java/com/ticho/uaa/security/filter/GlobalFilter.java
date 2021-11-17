package com.ticho.uaa.security.filter;

import com.ticho.uaa.security.SecurityConst;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 *  全局过滤器
 *
 * <p>
 *     功能<br/>
 *     1. 如果是放行的接口，给header中Authorization的value赋空值，这样security就不会校验token信息
 *     2. 可以不输入Bearer前缀也可以校验了
 * </p>
 * @author AdoroTutto
 * @date 2021-08-21 18:07
 */
@Getter
@Component
public class GlobalFilter extends OncePerRequestFilter {

    private List<RequestMatcher> requestMatchers;

    @PostConstruct
    public void init() {
        List<RequestMatcher> matcherList = new ArrayList<>();
        for (String s : SecurityConst.RELEASE_URL) {
            RequestMatcher requestMatcher = new OrRequestMatcher(new AntPathRequestMatcher(s));
            matcherList.add(requestMatcher);
        }
        this.requestMatchers = matcherList;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        boolean isMatch = false;
        // 统一放行
        for (RequestMatcher requestMatcher : requestMatchers) {
            if (requestMatcher.matches(request)) {
                isMatch = true;
                break;
            }
        }
        Vector<String> strings = new Vector<>();
        if (isMatch) {
            request = new HttpServletRequestWrapper(request) {
                /**
                 * 一旦放行全局url，给header中Authorization的value赋空值，这样security就不会校验token信息了
                 */
                @Override
                public Enumeration<String> getHeaders(String name) {
                    if (SecurityConst.AUTHORIZATION.equalsIgnoreCase(name)) {
                        return strings.elements();
                    }
                    return super.getHeaders(name);
                }

            };
        } else {
            request = new HttpServletRequestWrapper(request) {
                /**
                 * 如果 header 中有Authorization,value有 bearer正常返回，否则添加进去
                 */
                @Override
                public Enumeration<String> getHeaders(String name) {
                    if (SecurityConst.AUTHORIZATION.equalsIgnoreCase(name)) {
                        Enumeration<String> headers = super.getHeaders(name);
                        if (Objects.nonNull(headers) && headers.hasMoreElements()) {
                            String auth = headers.nextElement();
                            if (this.hasNotAuthPrefix(auth)) {
                                strings.add(SecurityConst.AUTHORIZATION + " " + auth);
                                return strings.elements();
                            }
                        }
                    }
                    return super.getHeaders(name);
                }

                @Override
                public String getHeader(String name) {
                    if (SecurityConst.AUTHORIZATION.equalsIgnoreCase(name)) {
                        String auth = super.getHeader(name);
                        if (this.hasNotAuthPrefix(auth)) {
                            return SecurityConst.BEARER + " " + super.getHeader(name);
                        }
                    }
                    return super.getHeader(name);
                }

                private boolean hasNotAuthPrefix(String auth) {
                    if (auth == null) {
                        return false;
                    }
                    auth = auth.toLowerCase();
                    return !auth.startsWith(SecurityConst.BEARER);
                }
            };
        }
        doFilter(request, response, filterChain);
    }
}
