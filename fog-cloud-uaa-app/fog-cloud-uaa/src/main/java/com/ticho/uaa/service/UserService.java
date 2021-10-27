package com.ticho.uaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.core.datasource.view.PageResult;
import com.ticho.uaa.entity.User;
import com.ticho.uaa.security.entity.SecurityUser;

/**
 * 用户 服务接口
 *
 * @author AdoroTutto
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
     * 根据账户名
     * @param username 账户名
     * @return SecurityUser
     */
    SecurityUser getByUsername(String username);

    /**
     * 根据手机号码
     * @param mobile 手机号码
     * @return SecurityUser
     */
    SecurityUser getByMobile(String mobile);

    /**
     * 根据邮箱
     * @param email 邮箱
     * @return SecurityUser
     */
    SecurityUser getByEmail(String email);

    /**
     * 根据微信号码
     * @param wechat 微信号码
     * @return SecurityUser
     */
    SecurityUser getByWechat(String wechat);

    /**
     * 根据QQ号码查询
     * @param qq QQ号码
     * @return SecurityUser
     */
    SecurityUser getByQq(String qq);

}

