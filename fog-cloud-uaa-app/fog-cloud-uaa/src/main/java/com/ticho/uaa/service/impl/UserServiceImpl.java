package com.ticho.uaa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.core.datasource.view.PageResult;
import com.ticho.uaa.entity.User;
import com.ticho.uaa.mapper.UserMapper;
import com.ticho.uaa.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户 服务实现
 *
 * @author AdoroTutto
 * @date 2021-10-24 22:12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 分页查询 用户 列表
     *
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @param user 条件
     * @return PageResult<User> 用户 列表
     */
    @Override
    public PageResult<User> page(int pageNum, int pageSize, User user){
        Page<User> page = PageHelper.startPage(pageNum, pageSize);
        baseMapper.selectByConditions(user);
        return new PageResult<>(page);
    }

}
