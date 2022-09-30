package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.User;

import java.util.List;

/**
 * 用户信息 Repository
 *
 * @author zhajianjun
 * @date 2022-09-30 16:56
 */
public interface UserRepository extends IService<User> {

    /**
     * 用户列表 查询
     *
     * @param user 用户
     *
     * @return {@link List}<{@link User}>
     */
    List<User> list(User user);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return {@link User}
     */
    User getByUsername(String username);

    /**
     * 根据手机号码查询用户
     *
     * @param mobile 手机号码
     * @return {@link User}
     */
    User getByMobile(String mobile);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return {@link User}
     */
    User getByEmail(String email);

    /**
     * 根据微信号码查询用户
     *
     * @param wechat 微信号码
     * @return {@link User}
     */
    User getByWechat(String wechat);

    /**
     * 根据QQ号码查询查询用户
     *
     * @param qq QQ号码
     * @return {@link User}
     */
    User getByQq(String qq);


}
