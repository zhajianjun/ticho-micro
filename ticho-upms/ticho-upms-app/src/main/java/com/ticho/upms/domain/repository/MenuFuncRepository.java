package com.ticho.upms.domain.repository;

import com.ticho.boot.datasource.service.RootService;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.infrastructure.entity.MenuFunc;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 菜单功能关联关系 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface MenuFuncRepository extends RootService<MenuFunc> {


    /**
     * 通过菜单id查询
     *
     * @param menuId 菜单id
     * @return {@link List}<{@link MenuFunc}>
     */
    List<MenuFunc> listByMenuId(Long menuId);

    /**
     * 通过菜单id列表查询功能号信息
     *
     * @return {@link Map}<{@link Long}, {@link List}<{@link Func}>>
     */
    Map<Long, List<Func>> getMenuFuncMap();

    /**
     * 通过菜单id列表查询功能号信息
     *
     * @param menuIds 菜单id
     * @return {@link Map}<{@link Long}, {@link List}<{@link Func}>>
     */
    Map<Long, List<Func>> getMenuFuncMapByMenuIds(List<Long> menuIds);


    /**
     * 根据菜单id列表删除
     *
     * @param menuIds 菜单id列表
     * @return boolean
     */
    boolean removeByMenuIds(Collection<Long> menuIds);

    /**
     * 根据功能号id列表删除
     *
     * @param funcIds 功能号id列表
     * @return boolean
     */
    boolean removeByFuncIds(Collection<Long> funcIds);

    /**
     * 根据菜单id和功能号id列表删除
     *
     * @param menuId 菜单id
     * @param funcIds 功能号id列表
     * @return boolean
     */
    boolean removeByMenuIdAndFuncIds(Long menuId, Collection<Long> funcIds);

}

