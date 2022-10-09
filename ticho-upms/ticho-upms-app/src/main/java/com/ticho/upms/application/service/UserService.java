package com.ticho.upms.application.service;

import com.ticho.upms.interfaces.dto.UpmsUserDTO;
import com.ticho.upms.interfaces.dto.UserDTO;

/**
 * 用户 服务接口
 *
 * @author zhajianjun
 * @date 2021-10-24 22:12
 */
public interface UserService {

    void saveUser(UserDTO userDTO);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return SecurityUser
     */
    UpmsUserDTO getByUsername(String username);

    /**
     * 根据手机号码查询用户
     *
     * @param mobile 手机号码
     * @return SecurityUser
     */
    UpmsUserDTO getByMobile(String mobile);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return SecurityUser
     */
    UpmsUserDTO getByEmail(String email);

    /**
     * 根据微信号码查询用户
     *
     * @param wechat 微信号码
     * @return SecurityUser
     */
    UpmsUserDTO getByWechat(String wechat);

    /**
     * 根据QQ号码查询查询用户
     *
     * @param qq QQ号码
     * @return SecurityUser
     */
    UpmsUserDTO getByQq(String qq);

}

