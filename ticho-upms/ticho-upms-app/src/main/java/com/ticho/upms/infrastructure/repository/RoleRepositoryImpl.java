package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.RoleRepository;
import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.infrastructure.mapper.RoleMapper;
import com.ticho.upms.interfaces.query.RoleQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 角色信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-08 17:45
 */
@Slf4j
@Service
public class RoleRepositoryImpl extends ServiceImpl<RoleMapper, Role> implements RoleRepository {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean save(Role role) {
        if (role == null) {
            log.info("角色信息保存异常，对象为null");
            return false;
        }
        return roleMapper.insert(role) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean saveBatch(Collection<Role> roles) {
        return this.saveBatch(roles, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean saveBatch(Collection<Role> roles, int batchSize) {
        if (CollUtil.isEmpty(roles)) {
            log.info("角色信息批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<Role>> split = CollUtil.split(roles, batchSize);
        Integer total = split.stream().map(roleMapper::insertBatch).reduce(1, Integer::sum);
        return total == roles.size();
    }

    @Override
    public boolean removeById(Serializable id) {
        if (id == null) {
            log.info("角色信息删除异常，主键ID为null");
            return false;
        }
        return roleMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return removeByIds(ids, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> ids, int batchSize) {
        if (CollUtil.isEmpty(ids)) {
            log.info("角色信息批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(ids, batchSize);
        Integer total = split.stream().map(roleMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == ids.size();
    }

    @Override
    public boolean updateById(Role role) {
        if (role == null) {
            log.info("角色信息更新异常，角色为null");
            return false;
        }
        return roleMapper.updateById(role) > 0;
    }

    @Override
    public Role getById(Serializable id) {
        return roleMapper.selectById(id);
    }

    @Override
    public List<Role> list(RoleQuery query) {
        return roleMapper.selectByConditions(query);
    }

}
