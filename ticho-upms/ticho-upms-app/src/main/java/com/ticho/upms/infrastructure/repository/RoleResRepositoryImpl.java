package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticho.upms.domain.repository.RoleResRepository;
import com.ticho.upms.infrastructure.entity.RoleRes;
import com.ticho.upms.infrastructure.mapper.RoleResMapper;
import com.ticho.upms.interfaces.query.RoleResQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 角色和资源关联表 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class RoleResRepositoryImpl extends ServiceImpl<RoleResMapper, RoleRes> implements RoleResRepository {

    @Autowired
    private RoleResMapper roleResMapper;

    @Override
    public boolean save(RoleRes roleRes) {
        if (roleRes == null) {
            log.info("角色和资源关联表保存异常，对象为null");
            return false;
        }
        return roleResMapper.insert(roleRes) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<RoleRes> roleRess) {
        return this.saveBatch(roleRess, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<RoleRes> roleRess, int batchSize) {
        if (CollUtil.isEmpty(roleRess)) {
            log.info("角色和资源关联表批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<RoleRes>> split = CollUtil.split(roleRess, batchSize);
        Integer total = split.stream().map(roleResMapper::insertBatch).reduce(1, Integer::sum);
        return total == roleRess.size();
    }

    @Override
    public boolean removeById(Serializable resId) {
        if (resId == null) {
            log.info("角色和资源关联表删除异常，主键ID为null");
            return false;
        }
        return roleResMapper.deleteById(resId) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> resIds) {
        return removeByIds(resIds, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> resIds, int batchSize) {
        if (CollUtil.isEmpty(resIds)) {
            log.info("角色和资源关联表批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(resIds, batchSize);
        Integer total = split.stream().map(roleResMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == resIds.size();
    }

    @Override
    public boolean updateById(RoleRes roleRes) {
        if (roleRes == null) {
            log.info("角色和资源关联表更新异常，角色为null");
            return false;
        }
        return roleResMapper.updateById(roleRes) > 0;
    }

    @Override
    public RoleRes getById(Serializable resId) {
        return roleResMapper.selectById(resId);
    }

    @Override
    public List<RoleRes> list(RoleResQuery query) {
        return roleResMapper.selectByConditions(query);
    }

}
