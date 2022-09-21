package com.ticho.upms.security.service;

import com.ticho.upms.security.entity.SecurityUser;

/**
 * 登录策略，根据登录类型查询Ticho放入线程变量里
 *
 * @author zhajianjun
 * @date 2020-07-04 19:13
 */
public interface LoginVerifyStrategy {

    /**
     * 登录校验
     *
     * @param username 登录账户
     */
    SecurityUser loadUserByUsername(String username);
}
