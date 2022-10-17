package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.UserRepository;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.infrastructure.mapper.UserMapper;
import com.ticho.upms.interfaces.dto.UserAccountDTO;
import com.ticho.upms.interfaces.query.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class UserRepositoryImpl extends RootServiceImpl<UserMapper, User> implements UserRepository {

    @Autowired
    private UserMapper userMapper;

    public User getByUsername(String tenantId, String username) {
        LambdaQueryWrapper<User> queryWrapper = getUserLambdaQueryWrapper();
        queryWrapper.eq(User::getTenantId, tenantId);
        queryWrapper.eq(User::getUsername, username);
        return getOne(queryWrapper);
    }

    private LambdaQueryWrapper<User> getUserLambdaQueryWrapper() {
        return Wrappers.lambdaQuery();
    }

    @Override
    public List<User> list(UserQuery query) {
        return userMapper.selectByConditions(query);
    }

    @Override
    public List<User> getByAccount(UserAccountDTO userAccountDTO) {
        // @formatter:off
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        String tenantId = userAccountDTO.getTenantId();
        String username = userAccountDTO.getUsername();
        String email = userAccountDTO.getEmail();
        String mobile = userAccountDTO.getMobile();
        List<Integer> status = userAccountDTO.getStatus();
        wrapper
            .eq(User::getTenantId, tenantId)
            .eq(CollUtil.isNotEmpty(status), User::getStatus, status)
            .and(x->
                x.eq(User::getUsername, username)
                 .or()
                 .eq(User::getEmail, email)
                 .or()
                 .eq(User::getMobile, mobile)
            );
        // @formatter:on
        return userMapper.selectList(wrapper);
    }

}
