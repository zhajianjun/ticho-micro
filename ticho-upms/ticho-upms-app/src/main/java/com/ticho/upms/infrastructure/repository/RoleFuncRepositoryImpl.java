package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.RoleFuncRepository;
import com.ticho.upms.infrastructure.entity.RoleFunc;
import com.ticho.upms.infrastructure.mapper.RoleFuncMapper;
import com.ticho.upms.interfaces.query.RoleFuncQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 角色功能关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class RoleFuncRepositoryImpl extends ServiceImpl<RoleFuncMapper, RoleFunc> implements RoleFuncRepository {

    @Autowired
    private RoleFuncMapper roleFuncMapper;

    @Override
    public boolean save(RoleFunc roleFunc) {
        if (roleFunc == null) {
            log.info("角色功能关联关系保存异常，对象为null");
            return false;
        }
        return roleFuncMapper.insert(roleFunc) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<RoleFunc> roleFuncs) {
        return this.saveBatch(roleFuncs, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<RoleFunc> roleFuncs, int batchSize) {
        if (CollUtil.isEmpty(roleFuncs)) {
            log.info("角色功能关联关系批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<RoleFunc>> split = CollUtil.split(roleFuncs, batchSize);
        Integer total = split.stream().map(roleFuncMapper::insertBatch).reduce(1, Integer::sum);
        return total == roleFuncs.size();
    }

    @Override
    public boolean removeById(Serializable roleId) {
        if (roleId == null) {
            log.info("角色功能关联关系删除异常，主键ID为null");
            return false;
        }
        return roleFuncMapper.deleteById(roleId) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> roleIds) {
        return removeByIds(roleIds, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> roleIds, int batchSize) {
        if (CollUtil.isEmpty(roleIds)) {
            log.info("角色功能关联关系批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(roleIds, batchSize);
        Integer total = split.stream().map(roleFuncMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == roleIds.size();
    }

    @Override
    public boolean updateById(RoleFunc roleFunc) {
        if (roleFunc == null) {
            log.info("角色功能关联关系更新异常，角色为null");
            return false;
        }
        return roleFuncMapper.updateById(roleFunc) > 0;
    }

    @Override
    public RoleFunc getById(Serializable roleId) {
        return roleFuncMapper.selectById(roleId);
    }

    @Override
    public List<RoleFunc> list(RoleFuncQuery query) {
        return roleFuncMapper.selectByConditions(query);
    }

}
