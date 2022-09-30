package com.ticho.upms.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.UserRepository;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户信息 Repository 实现
 *
 * @author zhajianjun
 * @date 2022-09-30 16:56
 */
@Repository
public class UserRepositoryImpl extends ServiceImpl<UserMapper, User> implements UserRepository {

    public List<User> list(User user) {
        return baseMapper.selectByConditions(user);
    }

    public User getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getUsername, username);
        return getOne(queryWrapper);
    }

    public User getByMobile(String mobile) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getMobile, mobile);
        return getOne(queryWrapper);
    }

    public User getByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getEmail, email);
        return getOne(queryWrapper);
    }

    public User getByWechat(String wechat) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getWechat, wechat);
        return getOne(queryWrapper);
    }

    public User getByQq(String qq) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getQq, qq);
        return getOne(queryWrapper);
    }

    private LambdaQueryWrapper<User> getUserLambdaQueryWrapper() {
        return Wrappers.lambdaQuery();
    }

}
