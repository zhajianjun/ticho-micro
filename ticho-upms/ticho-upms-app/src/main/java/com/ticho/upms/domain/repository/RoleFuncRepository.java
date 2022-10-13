package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.RoleFunc;
import com.ticho.upms.interfaces.query.RoleFuncQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 角色功能关联关系 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface RoleFuncRepository extends IService<RoleFunc> {

    /**
     * 保存角色功能关联关系
     *
     * @param roleFunc 角色功能关联关系 对象
     * @return boolean 是否保存成功
     */
    @Override
    boolean save(RoleFunc roleFunc);

    /**
     * 批量保存角色功能关联关系
     *
     * @param roleFunc 角色功能关联关系 对象集合
     * @return boolean 是否保存成功
     */
    @Override
    boolean saveBatch(Collection<RoleFunc> roleFunc);

    /**
     * 删除角色功能关联关系
     *
     * @param roleId 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable roleId);

    /**
     * 批量删除角色功能关联关系
     *
     * @param roleIds 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> roleIds);

    /**
     * 修改角色功能关联关系
     *
     * @param roleFunc 角色功能关联关系 对象
     * @return boolean 是否修改成功
     */
    @Override
    boolean updateById(RoleFunc roleFunc);

    /**
     * 根据roleId查询角色功能关联关系
     *
     * @param roleId 主键
     * @return {@link RoleFunc}
     */
    @Override
    RoleFunc getById(Serializable roleId);

    /**
     * 根据条件查询RoleFunc列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link RoleFunc}>
     */
    List<RoleFunc> list(RoleFuncQuery query);

}

