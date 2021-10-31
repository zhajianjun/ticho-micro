package com.ticho.uaa.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * 自定义OAuth2Exception异常转换
 *
 * @author AdoroTutto
 * @date 2021-08-17 12:44 下午
 */
public class Oauth2ExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {

        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        String oAuth2ErrorCode = oAuth2Exception.getOAuth2ErrorCode();
        if (OAuth2Exception.INVALID_GRANT.equals(oAuth2ErrorCode)) {
            return ResponseEntity.status(200).body(new CustomOauthException(e.getMessage()));
        }
        // 抛出CustomerOauth2Exception异常，会默认返回Oauth2ExceptionSerializer序列化自定义异常返回信息
        return ResponseEntity.status(oAuth2Exception.getHttpErrorCode()).body(new CustomOauthException(e.getMessage()));
    }
}
