package com.ticho.upms.domain.repository;

import com.ticho.boot.datasource.service.RootService;
import com.ticho.upms.infrastructure.entity.Tenant;
import com.ticho.upms.interfaces.query.TenantQuery;

import java.util.List;

/**
 * 租户信息 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface TenantRepository extends RootService<Tenant> {

    /**
     * 根据租户id修改租户信息
     *
     * @param tenant 租户信息 对象
     * @return boolean 是否修改成功
     */
    boolean updateByTenantId(Tenant tenant);

    /**
     * 根据租户id是查询
     *
     * @param tenantId 租户id
     * @return boolean
     */
    Tenant getByTenantId(String tenantId);

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
     * @param tenantId 租户id
     * @param status 租户状态列表，缺省则为查询全部
     * @return boolean
     */
    boolean exists(String tenantId, List<Integer> status);

}

