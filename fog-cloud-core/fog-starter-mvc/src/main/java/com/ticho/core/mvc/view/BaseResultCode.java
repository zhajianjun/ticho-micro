package com.ticho.core.mvc.view;

import com.ticho.core.mvc.enums.IResultCode;

import java.io.Serializable;

/**
 * 通用错误码
 *
 * @author AdoroTutto
 * @date 2021-10-16 23:52
 */
public enum BaseResultCode implements Serializable, IResultCode {

    // @formatter:off

    /**
     *
     */
    SUCCESS(0, "执行成功"),

    FAIL(-1, "执行失败");

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private final int code;
    /**
     *状态信息
     */
    private final String msg;

    BaseResultCode(int code, String msg) {
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
