package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.RoleMenu;
import com.ticho.upms.interfaces.query.RoleMenuQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 角色菜单关联关系 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface RoleMenuRepository extends IService<RoleMenu> {

    /**
     * 保存角色菜单关联关系
     *
     * @param roleMenu 角色菜单关联关系 对象
     * @return boolean 是否保存成功
     */
    @Override
    boolean save(RoleMenu roleMenu);

    /**
     * 批量保存角色菜单关联关系
     *
     * @param roleMenu 角色菜单关联关系 对象集合
     * @return boolean 是否保存成功
     */
    @Override
    boolean saveBatch(Collection<RoleMenu> roleMenu);

    /**
     * 删除角色菜单关联关系
     *
     * @param roleId 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable roleId);

    /**
     * 批量删除角色菜单关联关系
     *
     * @param roleIds 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> roleIds);

    /**
     * 修改角色菜单关联关系
     *
     * @param roleMenu 角色菜单关联关系 对象
     * @return boolean 是否修改成功
     */
    @Override
    boolean updateById(RoleMenu roleMenu);

    /**
     * 根据roleId查询角色菜单关联关系
     *
     * @param roleId 主键
     * @return {@link RoleMenu}
     */
    @Override
    RoleMenu getById(Serializable roleId);

    /**
     * 根据条件查询RoleMenu列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link RoleMenu}>
     */
    List<RoleMenu> list(RoleMenuQuery query);

}

