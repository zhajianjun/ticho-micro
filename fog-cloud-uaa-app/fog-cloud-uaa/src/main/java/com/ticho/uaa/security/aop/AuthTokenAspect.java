package com.ticho.uaa.security.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取token返回视图修改 AOP增强拦截
 *
 * @author AdoroTutto
 * @date 2021-08-21 18:32
 */
@Deprecated
public class AuthTokenAspect {
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenAspect.class);

    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    @SuppressWarnings("unchecked")
    public Object postAccessToken(ProceedingJoinPoint pjp) throws Throwable {
        // 放行
        Map<String, Object> resultMap = new HashMap<>(3);
        Object proceed = pjp.proceed();
        if (proceed != null) {
            ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
            OAuth2AccessToken body = responseEntity.getBody();
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                resultMap.put("code", 200);
                resultMap.put("msg", "获取授权码成功");
                resultMap.put("data", body);
            } else {
                logger.error("error:{}", responseEntity.getStatusCode().toString());
                resultMap.put("code", 401);
                resultMap.put("msg", "获取授权码失败");
                resultMap.put("data", body);
            }
        }
        return ResponseEntity.status(200).body(resultMap);
    }

    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.getAccessToken(..))")
    private Object getAccessToken(ProceedingJoinPoint pjp) throws Throwable {
        return this.postAccessToken(pjp);
    }
}
