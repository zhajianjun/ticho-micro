package com.ticho.upms.application.service;

import com.ticho.upms.interfaces.dto.UserDTO;
import com.ticho.upms.interfaces.dto.UserUpdDTO;

/**
 * 用户 服务接口
 *
 * @author zhajianjun
 * @date 2021-10-24 22:12
 */
public interface UserService {

    void saveUser(UserUpdDTO userUpdDTO);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return SecurityUser
     */
    UserDTO getByUsername(String username);

    /**
     * 根据手机号码查询用户
     *
     * @param mobile 手机号码
     * @return SecurityUser
     */
    UserDTO getByMobile(String mobile);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return SecurityUser
     */
    UserDTO getByEmail(String email);

    /**
     * 根据微信号码查询用户
     *
     * @param wechat 微信号码
     * @return SecurityUser
     */
    UserDTO getByWechat(String wechat);

    /**
     * 根据QQ号码查询查询用户
     *
     * @param qq QQ号码
     * @return SecurityUser
     */
    UserDTO getByQq(String qq);

}

