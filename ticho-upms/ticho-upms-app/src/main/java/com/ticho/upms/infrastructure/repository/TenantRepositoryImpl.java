package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ticho.upms.domain.repository.TenantRepository;
import com.ticho.upms.infrastructure.entity.Tenant;
import com.ticho.upms.infrastructure.mapper.TenantMapper;
import com.ticho.upms.interfaces.query.TenantQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 租户信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class TenantRepositoryImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantRepository {

    @Autowired
    private TenantMapper tenantMapper;

    @Override
    public boolean save(Tenant tenant) {
        if (tenant == null) {
            log.info("租户信息保存异常，对象为null");
            return false;
        }
        return tenantMapper.insert(tenant) == 1;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<Tenant> tenants) {
        return this.saveBatch(tenants, 200);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean saveBatch(Collection<Tenant> tenants, int batchSize) {
        if (CollUtil.isEmpty(tenants)) {
            log.info("租户信息批量保存异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<List<Tenant>> split = CollUtil.split(tenants, batchSize);
        Integer total = split.stream().map(tenantMapper::insertBatch).reduce(1, Integer::sum);
        return total == tenants.size();
    }

    @Override
    public boolean removeById(Serializable id) {
        if (id == null) {
            log.info("租户信息删除异常，主键ID为null");
            return false;
        }
        return tenantMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return removeByIds(ids, 200);
    }

    public boolean removeByIds(Collection<? extends Serializable> ids, int batchSize) {
        if (CollUtil.isEmpty(ids)) {
            log.info("租户信息批量删除异常，集合为null或者大小为0");
            return false;
        }
        if (batchSize <= 0 || batchSize > 1000) {
            batchSize = 200;
        }
        List<? extends List<? extends Serializable>> split = CollUtil.split(ids, batchSize);
        Integer total = split.stream().map(tenantMapper::deleteBatchIds).reduce(1, Integer::sum);
        return total == ids.size();
    }

    @Override
    public boolean updateById(Tenant tenant) {
        if (tenant == null) {
            log.info("租户信息更新异常，角色为null");
            return false;
        }
        return tenantMapper.updateById(tenant) > 0;
    }

    @Override
    public boolean updateByTenantId(Tenant tenant) {
        LambdaUpdateWrapper<Tenant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Tenant::getTenantId, tenant.getTenantId());
        return SqlHelper.retBool(tenantMapper.update(tenant, wrapper));
    }

    @Override
    public Tenant getById(Serializable id) {
        return tenantMapper.selectById(id);
    }

    @Override
    public Tenant getByTenantId(String tenantId) {
        LambdaQueryWrapper<Tenant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Tenant::getTenantId, tenantId);
        wrapper.last("limit 1");
        return tenantMapper.selectOne(wrapper);
    }

    @Override
    public List<Tenant> list(TenantQuery query) {
        return tenantMapper.selectByConditions(query);
    }

    @Override
    public boolean exists(String tenantId, List<Integer> status) {
        LambdaQueryWrapper<Tenant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Tenant::getTenantId, tenantId);
        wrapper.in(CollUtil.isNotEmpty(status), Tenant::getStatus, status);
        wrapper.select(Tenant::getId);
        wrapper.last("limit 1");
        return !tenantMapper.selectList(wrapper).isEmpty();
    }

}
