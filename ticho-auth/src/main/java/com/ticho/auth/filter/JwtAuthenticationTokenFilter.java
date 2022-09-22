package com.ticho.auth.filter;

import cn.hutool.core.util.StrUtil;
import com.ticho.auth.util.JwtTemplate;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.Result;
import com.ticho.boot.web.util.JsonUtil;
import com.ticho.common.security.dto.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 08:38:17
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info(request.getRequestURI());
        try {
            if (token != null && StrUtil.isNotBlank(token)) {
                String andVerify = JwtTemplate.decodeAndVerify(token);
                SecurityUser securityUser = JsonUtil.toJavaObject(andVerify, SecurityUser.class);
                if (securityUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, "N/A", securityUser.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            response.addHeader("Access-Control-Allow-origin", "http://localhost:8080");
            Result<String> retResult = Result.of(BizErrCode.FAIL, "抱歉，您的登录信息已过期，请重新登录");
            response.setContentType("application/json;charset=utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(JsonUtil.toJsonString(retResult));
        }
    }


}
