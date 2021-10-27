package com.ticho.uaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.core.datasource.view.PageResult;
import com.ticho.uaa.convert.UserConvert;
import com.ticho.uaa.entity.User;
import com.ticho.uaa.mapper.UserMapper;
import com.ticho.uaa.security.entity.SecurityUser;
import com.ticho.uaa.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 服务实现
 *
 * @author AdoroTutto
 * @date 2021-10-24 22:12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public PageResult<User> page(int pageNum, int pageSize, User user) {
        Page<User> page = PageHelper.startPage(pageNum, pageSize);
        baseMapper.selectByConditions(user);
        return new PageResult<>(page);
    }

    @Override
    public SecurityUser getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getUsername, username);
        User one = getOne(queryWrapper);
        SecurityUser securityUser = UserConvert.INSTANCE.userToSecurityUser(one);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public SecurityUser getByMobile(String mobile) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getMobile, mobile);
        User one = getOne(queryWrapper);
        SecurityUser securityUser = UserConvert.INSTANCE.userToSecurityUser(one);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public SecurityUser getByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getEmail, email);
        User one = getOne(queryWrapper);
        SecurityUser securityUser = UserConvert.INSTANCE.userToSecurityUser(one);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public SecurityUser getByWechat(String wechat) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getWechat, wechat);
        User one = getOne(queryWrapper);
        SecurityUser securityUser = UserConvert.INSTANCE.userToSecurityUser(one);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public SecurityUser getByQq(String qq) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getQq, qq);
        User one = getOne(queryWrapper);
        SecurityUser securityUser = UserConvert.INSTANCE.userToSecurityUser(one);
        setAuthorities(securityUser);
        return securityUser;
    }

    private LambdaQueryWrapper<User> getUserLambdaQueryWrapper() {
        return Wrappers.lambdaQuery();
    }

    private void setAuthorities(SecurityUser securityUser) {
        if (securityUser == null) {
            return;
        }
        // TODO 这部分预留权限
        SimpleGrantedAuthority admin = new SimpleGrantedAuthority("admin");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(admin);
        securityUser.setAuthorities(authorities);
    }


}
