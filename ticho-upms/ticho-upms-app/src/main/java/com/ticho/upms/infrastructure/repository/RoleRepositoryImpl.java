package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.RoleRepository;
import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.infrastructure.mapper.RoleMapper;
import com.ticho.upms.interfaces.query.RoleQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        wrapper.eq(Objects.nonNull(query.getId()), Role::getId, query.getId());
        wrapper.eq(StrUtil.isNotBlank(query.getCode()), Role::getCode, query.getCode());
        wrapper.eq(StrUtil.isNotBlank(query.getName()), Role::getName, query.getName());
        wrapper.eq(StrUtil.isNotBlank(query.getTenantId()), Role::getTenantId, query.getTenantId());
        wrapper.eq(StrUtil.isNotBlank(query.getRemark()), Role::getRemark, query.getRemark());
        wrapper.eq(Objects.nonNull(query.getVersion()), Role::getVersion, query.getVersion());
        wrapper.eq(StrUtil.isNotBlank(query.getCreateBy()), Role::getCreateBy, query.getCreateBy());
        wrapper.eq(Objects.nonNull(query.getCreateTime()), Role::getCreateTime, query.getCreateTime());
        wrapper.eq(StrUtil.isNotBlank(query.getUpdateBy()), Role::getUpdateBy, query.getUpdateBy());
        wrapper.eq(Objects.nonNull(query.getUpdateTime()), Role::getUpdateTime, query.getUpdateTime());
        wrapper.eq(Objects.nonNull(query.getIsDelete()), Role::getIsDelete, query.getIsDelete());
        return list(wrapper);
    }

    @Override
    public List<Role> listByCodes(List<String> codes) {
        if(CollUtil.isEmpty(codes)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Role::getCode, codes);
        return list(wrapper);
    }

}
