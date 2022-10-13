package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.UserRepository;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.infrastructure.mapper.UserMapper;
import com.ticho.upms.interfaces.query.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 用户信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class UserRepositoryImpl extends ServiceImpl<UserMapper, User> implements UserRepository {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean save(User user) {
        if (user == null) {
            log.info("用户信息保存异常，对象为null");
            return false;
        }
        return userMapper.insert(user) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<User> users) {
        return this.saveBatch(users, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<User> users, int batchSize) {
        if (CollUtil.isEmpty(users)) {
            log.info("用户信息批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<User>> split = CollUtil.split(users, batchSize);
        Integer total = split.stream().map(userMapper::insertBatch).reduce(1, Integer::sum);
        return total == users.size();
    }

    @Override
    public boolean removeById(Serializable id) {
        if (id == null) {
            log.info("用户信息删除异常，主键ID为null");
            return false;
        }
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return removeByIds(ids, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> ids, int batchSize) {
        if (CollUtil.isEmpty(ids)) {
            log.info("用户信息批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(ids, batchSize);
        Integer total = split.stream().map(userMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == ids.size();
    }

    @Override
    public boolean updateById(User user) {
        if (user == null) {
            log.info("用户信息更新异常，角色为null");
            return false;
        }
        return userMapper.updateById(user) > 0;
    }

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
    public User getById(Serializable id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> list(UserQuery query) {
        return userMapper.selectByConditions(query);
    }

}
