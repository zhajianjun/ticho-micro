package com.ticho.upms.domain.repository;

import com.ticho.boot.datasource.service.RootService;
import com.ticho.upms.infrastructure.entity.RoleFunc;

import java.util.Collection;
import java.util.List;

/**
 * 角色功能关联关系 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface RoleFuncRepository extends RootService<RoleFunc> {


    /**
     * 根据菜单id和功能号id删除
     *
     * @param menuId 菜单id
     * @param funcIds 功能号id
     * @return boolean
     */
    boolean removeByMenuIdAndFuncIds(Long menuId, Collection<Long> funcIds);


    /**
     * 根据角色id列表查询
     *
     * @param roleIds 角色id
     * @return {@link List}<{@link RoleFunc}>
     */
    List<RoleFunc> listByRoleIds(List<Long> roleIds);

}

