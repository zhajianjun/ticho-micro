package com.ticho.mvc.exception;

import com.ticho.mvc.enums.IResultCode;
import lombok.Getter;

/**
 * 业务异常处理
 *
 * @author AdoroTutto
 * @date 2020-08-20 23:08
 */
@Getter
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private final int code;

    /**
     *状态信息
     */
    private final String msg;

    public ServiceException(int code, String msg) {
        super("异常代码:" + code + ",异常信息:" + msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(IResultCode resultCode) {
        super("异常代码:" + resultCode.getCode() + ",异常信息:" + resultCode.getMsg());
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }


    public ServiceException(IResultCode resultCode, String msg) {
        super("异常代码:" + resultCode.getCode() + ",异常信息:" + msg);
        this.code = resultCode.getCode();
        this.msg = msg;
    }
}
