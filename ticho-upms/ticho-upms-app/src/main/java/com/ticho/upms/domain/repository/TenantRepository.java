package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.Tenant;
import com.ticho.upms.interfaces.query.TenantQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 租户信息 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface TenantRepository extends IService<Tenant> {

    /**
     * 保存租户信息
     *
     * @param tenant 租户信息 对象
     * @return boolean 是否保存成功
     */
    @Override
    boolean save(Tenant tenant);

    /**
     * 批量保存租户信息
     *
     * @param tenant 租户信息 对象集合
     * @return boolean 是否保存成功
     */
    @Override
    boolean saveBatch(Collection<Tenant> tenant);

    /**
     * 删除租户信息
     *
     * @param id 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 批量删除租户信息
     *
     * @param ids 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);

    /**
     * 修改租户信息
     *
     * @param tenant 租户信息 对象
     * @return boolean 是否修改成功
     */
    @Override
    boolean updateById(Tenant tenant);

    /**
     * 根据id查询租户信息
     *
     * @param id 主键
     * @return {@link Tenant}
     */
    @Override
    Tenant getById(Serializable id);

    /**
     * 根据条件查询Tenant列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Tenant}>
     */
    List<Tenant> list(TenantQuery query);

    /**
     * 查询租户id是否存在正常状态的
     *
     * @param tenantId 承租者id
     * @return boolean
     */
    boolean exists(String tenantId);
}

