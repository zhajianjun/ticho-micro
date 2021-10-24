package com.ticho.uaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.core.datasource.view.PageResult;
import com.ticho.uaa.entity.User;

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

}

