package com.ticho.upms.application.service;

import com.ticho.boot.security.dto.Oauth2AccessToken;
import com.ticho.boot.view.core.PageResult;
import com.ticho.upms.interfaces.dto.SignUpDTO;
import com.ticho.upms.interfaces.dto.UpmsUserDTO;
import com.ticho.upms.interfaces.dto.UserDTO;
import com.ticho.upms.interfaces.query.UserQuery;

import java.io.Serializable;

/**
 * 用户信息 服务接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface UserService {
    /**
     * 保存用户信息
     *
     * @param userDTO 用户信息DTO 对象
     */
    void save(UserDTO userDTO);

    /**
     * 删除用户信息
     *
     * @param id 主键
     */
    void removeById(Serializable id);

    /**
     * 修改用户信息
     *
     * @param userDTO 用户信息DTO 对象
     */
    void updateById(UserDTO userDTO);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return SecurityUser
     */
    UpmsUserDTO getByUsername(String username);

    /**
     * 根据id查询用户信息
     *
     * @param id 主键
     * @return {@link UserDTO}
     */
    UserDTO getById(Serializable id);

    /**
     * 分页查询用户信息列表
     *
     * @param query 查询
     * @return {@link PageResult}<{@link UserDTO}>
     */
    PageResult<UserDTO> page(UserQuery query);


    /**
     * 注册
     *
     * @param signUpDTO 注册dto
     */
    void signUp(SignUpDTO signUpDTO);
}

