package com.ticho.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.datasource.util.PageUtil;
import com.ticho.boot.view.core.PageResult;
import com.ticho.upms.convert.UserConvert;
import com.ticho.upms.dto.UserDTO;
import com.ticho.upms.entity.User;
import com.ticho.upms.mapper.UserMapper;
import com.ticho.upms.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 用户 服务实现
 *
 * @author zhajianjun
 * @date 2021-10-24 22:12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public PageResult<User> page(int pageNum, int pageSize, User user) {
        Page<User> page = PageHelper.startPage(pageNum, pageSize);
        baseMapper.selectByConditions(user);
        return PageUtil.getResult(page);
    }

    @Override
    public UserDTO getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getUsername, username);
        User one = getOne(queryWrapper);
        UserDTO securityUser = UserConvert.INSTANCE.userToDto(one);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public UserDTO getByMobile(String mobile) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getMobile, mobile);
        User one = getOne(queryWrapper);
        UserDTO securityUser = UserConvert.INSTANCE.userToDto(one);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public UserDTO getByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getEmail, email);
        User one = getOne(queryWrapper);
        UserDTO securityUser = UserConvert.INSTANCE.userToDto(one);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public UserDTO getByWechat(String wechat) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getWechat, wechat);
        User one = getOne(queryWrapper);
        UserDTO securityUser = UserConvert.INSTANCE.userToDto(one);
        setAuthorities(securityUser);
        return securityUser;
    }

    @Override
    public UserDTO getByQq(String qq) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getQq, qq);
        User one = getOne(queryWrapper);
        UserDTO securityUser = UserConvert.INSTANCE.userToDto(one);
        setAuthorities(securityUser);
        return securityUser;
    }

    private LambdaQueryWrapper<User> getUserLambdaQueryWrapper() {
        return Wrappers.lambdaQuery();
    }

    private void setAuthorities(UserDTO userDto) {
        if (userDto == null) {
            return;
        }
        userDto.setRoleIds(Collections.singletonList("admin"));
    }


}
