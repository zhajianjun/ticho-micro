package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.interfaces.query.RoleQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 角色信息 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-10 17:28
 */
public interface RoleRepository extends IService<Role> {

   /**
     * 保存角色信息
     *
     * @param role 角色信息 对象
     * @return boolean 是否保存成功
     */
    boolean save(Role role);

    /**
     * 批量保存角色信息
     *
     * @param role 角色信息 对象集合
     * @return boolean 是否保存成功
     */
    boolean saveBatch(Collection<Role> role);

    /**
     * 删除角色信息
     *
     * @param id 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 批量删除角色信息
     *
     * @param ids 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);

    /**
     * 修改角色信息
     *
     * @param role 角色信息 对象
     * @return boolean 是否修改成功
     */
    boolean updateById(Role role);

    /**
     * 根据id查询角色信息
     *
     * @param id 主键
     * @return {@link Role}
     */
    @Override
    Role getById(Serializable id);

    /**
     * 根据条件查询Role列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Role}>
     */
    List<Role> list(RoleQuery query);

}

