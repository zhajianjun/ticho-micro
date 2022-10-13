package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.MenuFunc;
import com.ticho.upms.interfaces.query.MenuFuncQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 菜单功能关联关系 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface MenuFuncRepository extends IService<MenuFunc> {

    /**
     * 保存菜单功能关联关系
     *
     * @param menuFunc 菜单功能关联关系 对象
     * @return boolean 是否保存成功
     */
    @Override
    boolean save(MenuFunc menuFunc);

    /**
     * 批量保存菜单功能关联关系
     *
     * @param menuFunc 菜单功能关联关系 对象集合
     * @return boolean 是否保存成功
     */
    @Override
    boolean saveBatch(Collection<MenuFunc> menuFunc);

    /**
     * 删除菜单功能关联关系
     *
     * @param menuId 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable menuId);

    /**
     * 批量删除菜单功能关联关系
     *
     * @param menuIds 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> menuIds);

    /**
     * 修改菜单功能关联关系
     *
     * @param menuFunc 菜单功能关联关系 对象
     * @return boolean 是否修改成功
     */
    @Override
    boolean updateById(MenuFunc menuFunc);

    /**
     * 根据menuId查询菜单功能关联关系
     *
     * @param menuId 主键
     * @return {@link MenuFunc}
     */
    @Override
    MenuFunc getById(Serializable menuId);

    /**
     * 根据条件查询MenuFunc列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link MenuFunc}>
     */
    List<MenuFunc> list(MenuFuncQuery query);

}

