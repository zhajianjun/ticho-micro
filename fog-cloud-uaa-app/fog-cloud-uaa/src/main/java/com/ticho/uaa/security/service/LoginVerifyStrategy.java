package com.ticho.uaa.security.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录策略，根据登录类型查询Ticho放入线程变量里
 *
 * @author AdoroTutto
 * @date 2020-07-04 19:13
 */
public interface LoginVerifyStrategy {
    /**
     * 登录校验
     * @param httpServletRequest 客户端的请求
     */
    void verify(HttpServletRequest httpServletRequest);
}
