package com.ticho.uaa.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * 自定义OAuth2Exception异常转换
 *
 * @author zhajianjun
 * @date 2021-08-17 12:44 下午
 */
@Slf4j
public class Oauth2ExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {

        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        String oAuth2ErrorCode = oAuth2Exception.getOAuth2ErrorCode();
        if (OAuth2Exception.INVALID_GRANT.equals(oAuth2ErrorCode)) {
            return ResponseEntity.status(200).body(new CustomOauthException(e.getMessage()));
        }
        //ResponseEntity.BodyBuilder status = ResponseEntity.status(oAuth2Exception.getHttpErrorCode());
        //String code = null;
        //String message = "认证失败";
        //log.error(message, e);
        //if (e instanceof UnsupportedGrantTypeException) {
        //    code = String.valueOf(((UnsupportedGrantTypeException) e).getHttpErrorCode());
        //    message = "不支持该认证类型";
        //    return status.body(Result.of(code, message));
        //}
        //if (e instanceof InvalidTokenException
        //        && StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token (expired)")) {
        //    code = String.valueOf(((InvalidTokenException) e).getHttpErrorCode());
        //    message = "刷新令牌已过期，请重新登录";
        //    status = ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        //    return status.body(DmpResult.data(code, message));
        //}
        //if (e instanceof InvalidScopeException) {
        //    code = String.valueOf(((InvalidScopeException) e).getHttpErrorCode());
        //    message = "不是有效的scope值";
        //    return status.body(DmpResult.data(code, message));
        //}
        //if (e instanceof RedirectMismatchException) {
        //    code = String.valueOf(((RedirectMismatchException) e).getHttpErrorCode());
        //    message = "redirect_uri值不正确";
        //    return status.body(DmpResult.data(code, message));
        //}
        //if (e instanceof BadClientCredentialsException) {
        //    code = String.valueOf(((BadClientCredentialsException) e).getHttpErrorCode());
        //    message = "client值不合法";
        //    status = ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        //    return status.body(DmpResult.data(code, message));
        //}
        //if (e instanceof UnsupportedResponseTypeException) {
        //    code = String.valueOf(((UnsupportedResponseTypeException) e).getHttpErrorCode());
        //    String codeMessage = StringUtils.substringBetween(e.getMessage(), "[", "]");
        //    message = codeMessage + "不是合法的response_type值";
        //    return status.body(DmpResult.data(code, message));
        //}
        //if (e instanceof InvalidGrantException) {
        //    code = String.valueOf(((InvalidGrantException) e).getHttpErrorCode());
        //    if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token")) {
        //        message = "refresh token无效";
        //        return status.body(DmpResult.data(code, message));
        //    }
        //    if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid authorization code")) {
        //        code = String.valueOf(((InvalidGrantException) e).getHttpErrorCode());
        //        String codeMessage = StringUtils.substringAfterLast(e.getMessage(), ": ");
        //        message = "授权码" + codeMessage + "不合法";
        //        return status.body(DmpResult.data(code, message));
        //    }
        //    if (StringUtils.containsIgnoreCase(e.getMessage(), "locked")) {
        //        message = "用户已被锁定，请联系管理员";
        //        return status.body(DmpResult.data(code, message));
        //    }
        //    message = "用户名或密码错误";
        //    return status.body(DmpResult.data(code, message));
        //}
        //
        //if (e instanceof UsernameNotFoundException) {
        //    message = e.getMessage();
        //    code = String.valueOf(HttpStatus.UNAUTHORIZED.value());
        //    return status.body(DmpResult.data(code, message));
        //}
        ////没翻译到的用默认
        //if (e instanceof Exception) {
        //    code = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
        //    return status.body(DmpResult.data(code, message));
        //}
        //code = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());


        // 抛出CustomerOauth2Exception异常，会默认返回Oauth2ExceptionSerializer序列化自定义异常返回信息
        return ResponseEntity.status(oAuth2Exception.getHttpErrorCode()).body(new CustomOauthException(e.getMessage()));
    }
}
