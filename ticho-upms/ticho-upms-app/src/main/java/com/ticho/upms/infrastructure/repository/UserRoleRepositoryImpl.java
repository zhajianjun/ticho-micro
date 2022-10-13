package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.UserRoleRepository;
import com.ticho.upms.infrastructure.entity.UserRole;
import com.ticho.upms.infrastructure.mapper.UserRoleMapper;
import com.ticho.upms.interfaces.query.UserRoleQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 用户角色关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class UserRoleRepositoryImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleRepository {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean save(UserRole userRole) {
        if (userRole == null) {
            log.info("用户角色关联关系保存异常，对象为null");
            return false;
        }
        return userRoleMapper.insert(userRole) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<UserRole> userRoles) {
        return this.saveBatch(userRoles, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<UserRole> userRoles, int batchSize) {
        if (CollUtil.isEmpty(userRoles)) {
            log.info("用户角色关联关系批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<UserRole>> split = CollUtil.split(userRoles, batchSize);
        Integer total = split.stream().map(userRoleMapper::insertBatch).reduce(1, Integer::sum);
        return total == userRoles.size();
    }

    @Override
    public boolean removeById(Serializable userId) {
        if (userId == null) {
            log.info("用户角色关联关系删除异常，主键ID为null");
            return false;
        }
        return userRoleMapper.deleteById(userId) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> userIds) {
        return removeByIds(userIds, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> userIds, int batchSize) {
        if (CollUtil.isEmpty(userIds)) {
            log.info("用户角色关联关系批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(userIds, batchSize);
        Integer total = split.stream().map(userRoleMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == userIds.size();
    }

    @Override
    public boolean updateById(UserRole userRole) {
        if (userRole == null) {
            log.info("用户角色关联关系更新异常，角色为null");
            return false;
        }
        return userRoleMapper.updateById(userRole) > 0;
    }

    @Override
    public UserRole getById(Serializable userId) {
        return userRoleMapper.selectById(userId);
    }

    @Override
    public List<UserRole> list(UserRoleQuery query) {
        return userRoleMapper.selectByConditions(query);
    }

}
