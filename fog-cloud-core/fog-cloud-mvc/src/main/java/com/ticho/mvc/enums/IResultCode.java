package com.ticho.mvc.enums;

/**
 *
 * @author AdoroTutto
 * @date 2021-10-16 20:24
 */
public interface IResultCode {

    /**
     * 返回码
     *
     * @return int
     */
    int getCode();

    /**
     * 返回消息
     *
     * @return String
     */
    String getMsg();
}
