package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.RoleMenuRepository;
import com.ticho.upms.infrastructure.entity.RoleMenu;
import com.ticho.upms.infrastructure.mapper.RoleMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 角色菜单关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class RoleMenuRepositoryImpl extends RootServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuRepository {

    @Override
    public List<RoleMenu> listByRoleIds(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<RoleMenu> wrapper = Wrappers.lambdaQuery();
        wrapper.in(RoleMenu::getRoleId, roleIds);
        return list(wrapper);
    }

    @Override
    public boolean removeByRoleIds(Collection<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return false;
        }
        LambdaQueryWrapper<RoleMenu> wrapper = Wrappers.lambdaQuery();
        wrapper.in(RoleMenu::getRoleId, roleIds);
        return remove(wrapper);
    }

    @Override
    public boolean removeByMenuIds(Collection<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return false;
        }
        LambdaQueryWrapper<RoleMenu> wrapper = Wrappers.lambdaQuery();
        wrapper.in(RoleMenu::getMenuId, menuIds);
        return remove(wrapper);
    }

    @Override
    public boolean removeByRoleIdAndMenuIds(Long roleId, Collection<Long> menuIds) {
        if (Objects.isNull(roleId) || CollUtil.isEmpty(menuIds)) {
            return false;
        }
        LambdaQueryWrapper<RoleMenu> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        wrapper.in(RoleMenu::getMenuId, menuIds);
        return remove(wrapper);
    }


}
