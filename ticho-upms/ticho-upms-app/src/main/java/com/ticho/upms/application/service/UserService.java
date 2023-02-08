package com.ticho.upms.application.service;

import com.ticho.boot.view.core.PageResult;
import com.ticho.upms.interfaces.dto.UserDTO;
import com.ticho.upms.interfaces.dto.UserPasswordDTO;
import com.ticho.upms.interfaces.dto.UserRoleDTO;
import com.ticho.upms.interfaces.dto.UserRoleMenuDtlDTO;
import com.ticho.upms.interfaces.dto.UserSignUpDTO;
import com.ticho.upms.interfaces.query.UserQuery;

/**
 * 用户信息 服务接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface UserService {

    /**
     * 注册
     *
     * @param userSignUpDTO 注册dto
     */
    void signUp(UserSignUpDTO userSignUpDTO);

    /**
     * 注册确认
     *
     * @param username 账户名称
     */
    void confirm(String username);

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
    void removeById(Long id);

    /**
     * 修改用户信息
     *
     * @param userDTO 用户信息DTO 对象
     */
    void updateById(UserDTO userDTO);

    /**
     * 根据id查询用户信息
     *
     * @param id 主键
     * @return {@link UserDTO}
     */
    UserDTO getById(Long id);

    /**
     * 分页查询用户信息列表
     *
     * @param query 查询
     * @return {@link PageResult}<{@link UserDTO}>
     */
    PageResult<UserDTO> page(UserQuery query);

    /**
     * 根据用户名查询用户
     *
     * @param tenantId 租户id
     * @param username 用户名
     * @return {@link UserRoleMenuDtlDTO}
     */
    UserRoleMenuDtlDTO getUserDtl(String tenantId, String username);

    /**
     * 绑定角色
     *
     * @param userRoleDTO 用户角色dto
     */
    void bindRole(UserRoleDTO userRoleDTO);

    /**
     * 更改用户密码
     *
     * @param userPasswordDTO 用户信息
     */
    void modifyPassword(UserPasswordDTO userPasswordDTO);

    /**
     * 验证码
     */
    void verifyByCode();

}

