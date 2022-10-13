package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.RoleRes;
import com.ticho.upms.interfaces.query.RoleResQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 角色和资源关联表 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface RoleResRepository extends IService<RoleRes> {

    /**
     * 保存角色和资源关联表
     *
     * @param roleRes 角色和资源关联表 对象
     * @return boolean 是否保存成功
     */
    @Override
    boolean save(RoleRes roleRes);

    /**
     * 批量保存角色和资源关联表
     *
     * @param roleRes 角色和资源关联表 对象集合
     * @return boolean 是否保存成功
     */
    @Override
    boolean saveBatch(Collection<RoleRes> roleRes);

    /**
     * 删除角色和资源关联表
     *
     * @param resId 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable resId);

    /**
     * 批量删除角色和资源关联表
     *
     * @param resIds 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> resIds);

    /**
     * 修改角色和资源关联表
     *
     * @param roleRes 角色和资源关联表 对象
     * @return boolean 是否修改成功
     */
    @Override
    boolean updateById(RoleRes roleRes);

    /**
     * 根据resId查询角色和资源关联表
     *
     * @param resId 主键
     * @return {@link RoleRes}
     */
    @Override
    RoleRes getById(Serializable resId);

    /**
     * 根据条件查询RoleRes列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link RoleRes}>
     */
    List<RoleRes> list(RoleResQuery query);

}

