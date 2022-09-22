package com.ticho.common.security.service;


import com.ticho.common.security.dto.SecurityUser;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 10:46
 */
public interface LoadUserService {

    SecurityUser loadUser(String account);

}
