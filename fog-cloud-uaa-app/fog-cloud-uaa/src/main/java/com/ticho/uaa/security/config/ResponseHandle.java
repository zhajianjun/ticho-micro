package com.ticho.uaa.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 基础全局异常处理
 *
 * @author AdoroTutto
 * @date 2021-10-16 20:24
 */
@Slf4j
@RestControllerAdvice
@Order(0)
public class ResponseHandle {

    /**
     * 全局错误用于捕获不可预知的异常
     */
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public void insufficientAuthenticationException(InsufficientAuthenticationException ex) {
        throw ex;
    }

    // @formatter:off

}
