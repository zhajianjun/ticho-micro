package com.ticho.common.security.filter;

import cn.hutool.core.util.StrUtil;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.Result;
import com.ticho.boot.view.exception.BizException;
import com.ticho.boot.view.util.Assert;
import com.ticho.boot.web.util.JsonUtil;
import com.ticho.common.security.constant.OAuth2Const;
import com.ticho.common.security.convert.JwtConverter;
import com.ticho.common.security.dto.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;


/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 08:38:17
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private JwtConverter jwtConverter;

    private UserDetailsChecker userDetailsChecker;

    public JwtAuthenticationTokenFilter(JwtConverter jwtConverter, UserDetailsChecker userDetailsChecker) {
        this.jwtConverter = jwtConverter;
        this.userDetailsChecker = userDetailsChecker;
    }


    // @formatter:off

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain chain
    ) throws IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if (StrUtil.isBlank(token)) {
                chain.doFilter(request, response);
                return;
            }
            Map<String, Object> decodeAndVerify = jwtConverter.decodeAndVerify(token);
            Object type = decodeAndVerify.getOrDefault(OAuth2Const.TYPE, "");
            Assert.isTrue(Objects.equals(type, OAuth2Const.ACCESS_TOKEN), BizErrCode.FAIL, "token不合法");
            SecurityUser securityUser = JsonUtil.convert(decodeAndVerify, SecurityUser.class);
            if (securityUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                userDetailsChecker.check(securityUser);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUser, "N/A", securityUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            String message = e.getMessage();
            log.warn("catch error\t{}", message);
            if (e instanceof BizException) {
                message = ((BizException) e).getMsg();
            }
            Result<String> retResult = Result.of(BizErrCode.FAIL, message);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(JsonUtil.toJsonString(retResult));
        }
    }

}
