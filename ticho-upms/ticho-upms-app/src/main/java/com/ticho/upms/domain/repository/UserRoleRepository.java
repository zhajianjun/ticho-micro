package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.UserRole;
import com.ticho.upms.interfaces.query.UserRoleQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 用户角色关联关系 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface UserRoleRepository extends IService<UserRole> {

    /**
     * 保存用户角色关联关系
     *
     * @param userRole 用户角色关联关系 对象
     * @return boolean 是否保存成功
     */
    @Override
    boolean save(UserRole userRole);

    /**
     * 批量保存用户角色关联关系
     *
     * @param userRole 用户角色关联关系 对象集合
     * @return boolean 是否保存成功
     */
    @Override
    boolean saveBatch(Collection<UserRole> userRole);

    /**
     * 删除用户角色关联关系
     *
     * @param userId 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable userId);

    /**
     * 批量删除用户角色关联关系
     *
     * @param userIds 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> userIds);

    /**
     * 修改用户角色关联关系
     *
     * @param userRole 用户角色关联关系 对象
     * @return boolean 是否修改成功
     */
    @Override
    boolean updateById(UserRole userRole);

    /**
     * 根据userId查询用户角色关联关系
     *
     * @param userId 主键
     * @return {@link UserRole}
     */
    @Override
    UserRole getById(Serializable userId);

    /**
     * 根据条件查询UserRole列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link UserRole}>
     */
    List<UserRole> list(UserRoleQuery query);

}

