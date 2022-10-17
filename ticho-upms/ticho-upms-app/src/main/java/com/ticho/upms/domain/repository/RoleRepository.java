package com.ticho.upms.domain.repository;

import com.ticho.boot.datasource.service.RootService;
import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.interfaces.query.RoleQuery;

import java.util.List;

/**
 * 角色信息 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface RoleRepository extends RootService<Role> {

    /**
     * 根据条件查询Role列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Role}>
     */
    List<Role> list(RoleQuery query);

}

