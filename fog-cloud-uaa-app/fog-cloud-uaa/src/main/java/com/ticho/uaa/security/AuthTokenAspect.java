package com.ticho.uaa.security;

import com.ticho.commons.view.ResultView;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * 获取token返回视图修改
 *
 * @author AdoroTutto
 * @date 2021-08-21 18:32
 */
//@Component
//@Aspect
public class AuthTokenAspect {
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenAspect.class);

    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    @SuppressWarnings("unchecked")
    public Object postAccessToken(ProceedingJoinPoint pjp) throws Throwable {
        ResultView<OAuth2AccessToken> resultView = null;
        Object proceed = pjp.proceed();
        if (proceed != null) {
            ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
            OAuth2AccessToken body = responseEntity.getBody();
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                resultView = ResultView.ok(body);

            } else {
                logger.error("error:{}", responseEntity.getStatusCode().toString());
                resultView = ResultView.fail();
                resultView.setData(body);
            }
        }
        return ResponseEntity.status(200).body(resultView);
    }

    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.getAccessToken(..))")
    private Object getAccessToken(ProceedingJoinPoint pjp) throws Throwable {
        return this.postAccessToken(pjp);
    }
}
