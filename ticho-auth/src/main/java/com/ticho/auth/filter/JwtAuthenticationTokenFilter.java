package com.ticho.auth.filter;

import cn.hutool.core.util.StrUtil;
import com.ticho.auth.util.JwtTemplate;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.Result;
import com.ticho.boot.web.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * @Project: security_jwt
 * @Author: AdoroTutto
 * @Date: 2019-08-14 14:55
 * @Description:
 */

@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationTokenFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(request.getRequestURL());
        try {
            if (token != null && StrUtil.isNotBlank(token)) {
                String andVerify = JwtTemplate.decodeAndVerify(token);
                Map<String, String> map = JsonUtil.toMap(andVerify, String.class, String.class);
                String username = map.get("username");
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            response.addHeader("Access-Control-Allow-origin", "http://localhost:8080");
            Result<String> retResult = Result.of(BizErrCode.FAIL, "抱歉，您的登录信息已过期，请重新登录");
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.toJsonString(retResult));
        }
    }


}
