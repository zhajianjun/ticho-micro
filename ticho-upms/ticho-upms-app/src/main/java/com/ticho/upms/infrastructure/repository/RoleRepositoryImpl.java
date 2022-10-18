package com.ticho.upms.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.RoleRepository;
import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.infrastructure.mapper.RoleMapper;
import com.ticho.upms.interfaces.query.RoleQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色信息 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class RoleRepositoryImpl extends RootServiceImpl<RoleMapper, Role> implements RoleRepository {

    @Override
    public List<Role> list(RoleQuery query) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Role::getId, query.getId());
        wrapper.eq(Role::getCode, query.getCode());
        wrapper.eq(Role::getName, query.getName());
        wrapper.eq(Role::getTenantId, query.getTenantId());
        wrapper.eq(Role::getRemark, query.getRemark());
        wrapper.eq(Role::getVersion, query.getVersion());
        wrapper.eq(Role::getCreateBy, query.getCreateBy());
        wrapper.eq(Role::getCreateTime, query.getCreateTime());
        wrapper.eq(Role::getUpdateBy, query.getUpdateBy());
        wrapper.eq(Role::getUpdateTime, query.getUpdateTime());
        wrapper.eq(Role::getIsDelete, query.getIsDelete());
        return list(wrapper);
    }

}
