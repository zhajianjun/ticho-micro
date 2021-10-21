package com.ticho.core.mvc.view;

import com.ticho.core.mvc.enums.IResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应消息
 *
 * @author AdoroTutto
 * @date 2021-10-16 20:24
 */
@Data
@ApiModel(value = "统一响应消息")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码", required = true, position = 10)
    private int code;

    @ApiModelProperty(value = "消息内容", required = true, position = 20)
    private String msg;

    @ApiModelProperty(value = "业务数据", position = 30)
    private T data;

    @ApiModelProperty(value = "时间戳", required = true, position = 40)
    private long time;

    public Result() {
        this.code = BaseResultCode.SUCCESS.getCode();
        this.msg = BaseResultCode.SUCCESS.getMsg();
        this.time = System.currentTimeMillis();
    }

    public Result(IResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.time = System.currentTimeMillis();
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.time = System.currentTimeMillis();
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    public Result<T> code(int code) {
        this.code = code;
        return this;
    }

    public Result<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    public static <T> Result<T> of(int code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> of(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> of(IResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMsg());
    }

    public static <T> Result<T> of(IResultCode resultCode, T data) {
        return new Result<>(resultCode.getCode(), resultCode.getMsg(), data);
    }

    public static <T> Result<T> of(IResultCode resultCode, String msg, T data) {
        return new Result<>(resultCode.getCode(), msg, data);
    }

    public static <T> Result<T> ok() {
        return new Result<>();
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(BaseResultCode.SUCCESS.getCode(), BaseResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(BaseResultCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> Result<T> fail() {
        return of(BaseResultCode.FAIL);
    }

    public static <T> Result<T> fail(String msg) {
        return of(BaseResultCode.FAIL.getCode(), msg, null);
    }

    public static <T> Result<T> condition(boolean flag) {
        return flag ? ok() : fail();
    }
}
