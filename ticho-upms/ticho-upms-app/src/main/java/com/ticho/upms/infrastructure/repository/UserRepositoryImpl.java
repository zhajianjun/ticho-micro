package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.UserRepository;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.infrastructure.mapper.UserMapper;
import com.ticho.upms.interfaces.query.UserAccountQuery;
import com.ticho.upms.interfaces.query.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 用户信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class UserRepositoryImpl extends RootServiceImpl<UserMapper, User> implements UserRepository {

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
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Objects.nonNull(query.getId()), User::getId, query.getId());
        wrapper.eq(StrUtil.isNotBlank(query.getUsername()), User::getUsername, query.getUsername());
        wrapper.eq(StrUtil.isNotBlank(query.getPassword()), User::getPassword, query.getPassword());
        wrapper.eq(StrUtil.isNotBlank(query.getNickname()), User::getNickname, query.getNickname());
        wrapper.eq(StrUtil.isNotBlank(query.getRealname()), User::getRealname, query.getRealname());
        wrapper.eq(StrUtil.isNotBlank(query.getIdcard()), User::getIdcard, query.getIdcard());
        wrapper.eq(Objects.nonNull(query.getSex()), User::getSex, query.getSex());
        wrapper.eq(Objects.nonNull(query.getAge()), User::getAge, query.getAge());
        wrapper.eq(Objects.nonNull(query.getBirthday()), User::getBirthday, query.getBirthday());
        wrapper.eq(StrUtil.isNotBlank(query.getAddress()), User::getAddress, query.getAddress());
        wrapper.eq(StrUtil.isNotBlank(query.getEducation()), User::getEducation, query.getEducation());
        wrapper.eq(StrUtil.isNotBlank(query.getEmail()), User::getEmail, query.getEmail());
        wrapper.eq(Objects.nonNull(query.getQq()), User::getQq, query.getQq());
        wrapper.eq(StrUtil.isNotBlank(query.getWechat()), User::getWechat, query.getWechat());
        wrapper.eq(StrUtil.isNotBlank(query.getMobile()), User::getMobile, query.getMobile());
        wrapper.eq(StrUtil.isNotBlank(query.getPhoto()), User::getPhoto, query.getPhoto());
        wrapper.eq(StrUtil.isNotBlank(query.getLastIp()), User::getLastIp, query.getLastIp());
        wrapper.eq(Objects.nonNull(query.getLastTime()), User::getLastTime, query.getLastTime());
        wrapper.eq(Objects.nonNull(query.getStatus()), User::getStatus, query.getStatus());
        wrapper.eq(StrUtil.isNotBlank(query.getTenantId()), User::getTenantId, query.getTenantId());
        wrapper.eq(StrUtil.isNotBlank(query.getRemark()), User::getRemark, query.getRemark());
        wrapper.eq(Objects.nonNull(query.getVersion()), User::getVersion, query.getVersion());
        wrapper.eq(StrUtil.isNotBlank(query.getCreateBy()), User::getCreateBy, query.getCreateBy());
        wrapper.eq(Objects.nonNull(query.getCreateTime()), User::getCreateTime, query.getCreateTime());
        wrapper.eq(StrUtil.isNotBlank(query.getUpdateBy()), User::getUpdateBy, query.getUpdateBy());
        wrapper.eq(Objects.nonNull(query.getUpdateTime()), User::getUpdateTime, query.getUpdateTime());
        wrapper.eq(Objects.nonNull(query.getIsDelete()), User::getIsDelete, query.getIsDelete());
        return list(wrapper);
    }

    @Override
    public List<User> getByAccount(UserAccountQuery userAccountQuery) {
        // @formatter:off
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        String tenantId = userAccountQuery.getTenantId();
        String username = userAccountQuery.getUsername();
        String email = userAccountQuery.getEmail();
        String mobile = userAccountQuery.getMobile();
        List<Integer> status = userAccountQuery.getStatus();
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
        return list(wrapper);
    }

}
