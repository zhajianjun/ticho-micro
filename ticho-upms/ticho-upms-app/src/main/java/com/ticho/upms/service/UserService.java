package com.ticho.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.boot.view.core.PageResult;
import com.ticho.upms.dto.UserDTO;
import com.ticho.upms.entity.User;

/**
 * 用户 服务接口
 *
 * @author zhajianjun
 * @date 2021-10-24 22:12
 */
public interface UserService extends IService<User> {

    /**
     * 分页查询 用户 列表
     *
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @param user 条件
     * @return PageInfo<User> 用户 列表
     */
    PageResult<User> page(int pageNum, int pageSize, User user);

    /**
     * 根据账户名查询用户
     *
     * @param username 账户名
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

