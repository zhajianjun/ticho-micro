package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.TenantRepository;
import com.ticho.upms.infrastructure.entity.Tenant;
import com.ticho.upms.infrastructure.mapper.TenantMapper;
import com.ticho.upms.interfaces.query.TenantQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 租户信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class TenantRepositoryImpl extends RootServiceImpl<TenantMapper, Tenant> implements TenantRepository {

    @Autowired
    private TenantMapper tenantMapper;


    @Override
    public boolean updateByTenantId(Tenant tenant) {
        LambdaUpdateWrapper<Tenant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Tenant::getTenantId, tenant.getTenantId());
        return SqlHelper.retBool(tenantMapper.update(tenant, wrapper));
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
        LambdaQueryWrapper<Tenant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Tenant::getId, query.getId());
        wrapper.eq(Tenant::getTenantId, query.getTenantId());
        wrapper.eq(Tenant::getTenantName, query.getTenantName());
        wrapper.eq(Tenant::getRemark, query.getRemark());
        wrapper.eq(Tenant::getStatus, query.getStatus());
        wrapper.eq(Tenant::getVersion, query.getVersion());
        wrapper.eq(Tenant::getCreateBy, query.getCreateBy());
        wrapper.eq(Tenant::getCreateTime, query.getCreateTime());
        wrapper.eq(Tenant::getUpdateBy, query.getUpdateBy());
        wrapper.eq(Tenant::getUpdateTime, query.getUpdateTime());
        wrapper.eq(Tenant::getIsDelete, query.getIsDelete());
        return list(wrapper);
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
