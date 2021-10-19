package com.ticho.core.mvc.exception;

import com.ticho.core.mvc.view.Result;
import com.ticho.core.mvc.annotation.View;
import com.ticho.core.mvc.view.HttpResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 基础全局异常处理
 *
 * @author AdoroTutto
 * @date 2021-10-16 20:24
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 10)
public class BaseResponseHandle implements ResponseBodyAdvice<Object> {

    /**
     * 全局错误用于捕获不可预知的异常
     */
    @ExceptionHandler(Exception.class)
    public Result<String> exception(Exception ex, HttpServletRequest req, HttpServletResponse res) {
        Result<String> result;
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            result = Result.of(HttpResultCode.METHOD_NOT_ALLOWED);
            res.setStatus(result.getCode());
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            result = Result.of(HttpResultCode.UNSUPPORTED_MEDIA_TYPE);
            res.setStatus(result.getCode());
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            result = Result.of(HttpResultCode.NOT_ACCEPTABLE);
            res.setStatus(result.getCode());
        } else if (ex instanceof MissingPathVariableException) {
            result = Result.of(HttpResultCode.INTERNAL_SERVER_ERROR);
            res.setStatus(result.getCode());
        } else if (ex instanceof MissingServletRequestParameterException) {
            result = Result.of(HttpResultCode.BAD_REQUEST);
            res.setStatus(result.getCode());
        } else if (ex instanceof ServletRequestBindingException) {
            result = Result.of(HttpResultCode.BAD_REQUEST);
            res.setStatus(result.getCode());
        } else if (ex instanceof ConversionNotSupportedException) {
            result = Result.of(HttpResultCode.INTERNAL_SERVER_ERROR);
            res.setStatus(result.getCode());
        } else if (ex instanceof TypeMismatchException) {
            result = Result.of(HttpResultCode.BAD_REQUEST);
            res.setStatus(result.getCode());
        } else if (ex instanceof HttpMessageNotReadableException) {
            result = Result.of(HttpResultCode.BAD_REQUEST);
            res.setStatus(result.getCode());
        } else if (ex instanceof HttpMessageNotWritableException) {
            result = Result.of(HttpResultCode.INTERNAL_SERVER_ERROR);
            res.setStatus(result.getCode());
        } else if (ex instanceof MethodArgumentNotValidException) {
            result = Result.of(HttpResultCode.BAD_REQUEST);
            res.setStatus(result.getCode());
        } else if (ex instanceof MissingServletRequestPartException) {
            result = Result.of(HttpResultCode.BAD_REQUEST);
            res.setStatus(result.getCode());
        } else if (ex instanceof BindException) {
            result = Result.of(HttpResultCode.BAD_REQUEST);
            res.setStatus(result.getCode());
        } else if (ex instanceof NoHandlerFoundException) {
            result = Result.of(HttpResultCode.NOT_FOUND);
            res.setStatus(result.getCode());
        } else if (ex instanceof AsyncRequestTimeoutException) {
            result = Result.of(HttpResultCode.SERVICE_UNAVAILABLE);
            res.setStatus(result.getCode());
        } else if (ex instanceof SystemException) {
            // 系统异常
            SystemException systemException = (SystemException) ex;
            result = Result.of(systemException.getCode(), systemException.getMsg());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else if (ex instanceof ServiceException) {
            // 业务异常
            ServiceException serviceException = (ServiceException) ex;
            result = Result.of(serviceException.getCode(), serviceException.getMsg());
            res.setStatus(HttpStatus.OK.value());
            result.setData(req.getRequestURI());
            log.warn("catch error\t{}\n{}", ex.getMessage(), result.getMsg(), ex);
            return result;
        } else {
            // 未知异常
            result = Result.of(HttpResultCode.FAILED);
            res.setStatus(result.getCode());
        }
        result.setData(req.getRequestURI());
        log.error("catch error\t{}\n{}", ex.getMessage(), result.getMsg(), ex);
        return result;
    }

    // @formatter:off

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        Class<?> declaringClass = methodParameter.getDeclaringClass();
        View typeView = declaringClass.getDeclaredAnnotation(View.class);
        View methodView = methodParameter.getMethodAnnotation(View.class);
        // action对应的类上是否加了注解，且ignore = false
        // 仅方法上有注解，或者该注解 ignore=false
        if (typeView != null && !typeView.ignore()) {
            // 方法上没有注解，或者该注解 ignore=false
            return methodView == null || !methodView.ignore();
        } else {
            return methodView != null && !methodView.ignore();
        }
    }

    @Override
    public Object beforeBodyWrite(Object o, @NonNull MethodParameter methodParameter, @NonNull MediaType mediaType, @NonNull Class<? extends HttpMessageConverter<?>> aClass, @NonNull ServerHttpRequest serverHttpRequest, @NonNull ServerHttpResponse serverHttpResponse) {
        if (o instanceof Result) {
            return o;
        }
        return Result.ok(o);
    }
}
