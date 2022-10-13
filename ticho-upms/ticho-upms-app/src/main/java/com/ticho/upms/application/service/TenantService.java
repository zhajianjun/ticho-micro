package com.ticho.upms.application.service;

import com.ticho.boot.view.core.PageResult;
import com.ticho.upms.interfaces.dto.TenantDTO;
import com.ticho.upms.interfaces.query.TenantQuery;

import java.io.Serializable;

/**
 * 租户信息 服务接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface TenantService {
    /**
     * 保存租户信息
     *
     * @param tenantDTO 租户信息DTO 对象
     */
    void save(TenantDTO tenantDTO);

    /**
     * 删除租户信息
     *
     * @param id 主键
     */
    void removeById(Serializable id);

    /**
     * 修改租户信息
     *
     * @param tenantDTO 租户信息DTO 对象
     */
    void updateById(TenantDTO tenantDTO);

    /**
     * 根据id查询租户信息
     *
     * @param id 主键
     * @return {@link TenantDTO}
     */
    TenantDTO getById(Serializable id);

    /**
     * 分页查询租户信息列表
     *
     * @param query 查询
     * @return {@link PageResult}<{@link TenantDTO}>
     */
    PageResult<TenantDTO> page(TenantQuery query);

}

