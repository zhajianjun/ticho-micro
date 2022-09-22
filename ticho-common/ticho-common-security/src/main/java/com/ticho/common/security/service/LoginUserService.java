package com.ticho.common.security.service;


import com.ticho.common.security.dto.SecurityUser;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 10:46
 */
public interface LoginUserService {

    /**
     * 根据凭证获取用户信息
     *
     * @param account 账户
     * @param credentials 凭证
     * @return 用户信息
     */
    SecurityUser login(String account, String credentials);

}
