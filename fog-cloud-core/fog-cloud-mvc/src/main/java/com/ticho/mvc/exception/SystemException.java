package com.ticho.mvc.exception;

import com.ticho.mvc.enums.IResultCode;
import lombok.Getter;

/**
 * 系统异常处理
 *
 * @author AdoroTutto
 * @date 2021-10-16 20:24
 */
@Getter
public class SystemException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private final int code;

    /**
     *状态信息
     */
    private final String msg;

    public SystemException(int code, String msg) {
        super("异常代码:" + code + ",异常信息:" + msg);
        this.code = code;
        this.msg = msg;
    }

    public SystemException(IResultCode resultCode) {
        super("异常代码:" + resultCode.getCode() + ",异常信息:" + resultCode.getMsg());
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public SystemException(IResultCode resultCode, String msg) {
        super("异常代码:" + resultCode.getCode() + ",异常信息:" + msg);
        this.code = resultCode.getCode();
        this.msg = msg;
    }
}
