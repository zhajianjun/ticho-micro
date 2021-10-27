package com.ticho.uaa.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * 自定义OAuth2Exception异常转换
 *
 * @author AdoroTutto
 * @date 2021-08-17 12:44 下午
 */
@Component
public class Oauth2ExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        // 抛出CustomerOauth2Exception异常，会默认返回Oauth2ExceptionSerializer序列化自定义异常返回信息
        return ResponseEntity.status(oAuth2Exception.getHttpErrorCode()).body(new CustomOauthException(e.getMessage()));
    }
}
