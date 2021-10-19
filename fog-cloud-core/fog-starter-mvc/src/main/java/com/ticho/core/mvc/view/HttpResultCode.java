package com.ticho.core.mvc.view;

import com.ticho.core.mvc.enums.IResultCode;

import java.io.Serializable;

/**
 * 状态错误码
 *
 * @author AdoroTutto
 * @date 2021-10-16 20:24
 */
public enum HttpResultCode implements Serializable, IResultCode {

    // @formatter:off

    /**
     *
     */
    SUCCESS(200, "执行成功"),

    BAD_REQUEST(400, "错误的请求"),

    NOT_LOGIN(401, "请登录"),

    TOKEN_INVALID(401, "TOKEN 失效"),

    NOT_ACTIVED(402, "用户未激活"),

    ACCESS_DENIED(403, "权限不足"),

    NOT_FOUND(404, "啥都没有"),

    METHOD_NOT_ALLOWED(405, "不允许的方法"),

    NOT_ACCEPTABLE(406, "Not Acceptable"),

    UNSUPPORTED_MEDIA_TYPE(415, "不受支持的媒体类型"),

    FAILED(500, "执行失败"),

    INTERNAL_SERVER_ERROR(500, "系统异常"),

    BAD_GATEWAY(502, "错误的网关"),

    SERVICE_UNAVAILABLE(503, "服务不可用");

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private final int code;

    /**
     *状态信息
     */
    private final String msg;

    HttpResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public String toString() {
        return String.format(" %s:{code=%s, msg=%s} ", this.getClass().getSimpleName(), this.code, this.msg);
    }
}
