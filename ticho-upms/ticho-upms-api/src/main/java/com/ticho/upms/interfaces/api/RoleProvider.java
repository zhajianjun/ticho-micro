package com.ticho.upms.interfaces.api;

import com.ticho.boot.view.core.Result;
import com.ticho.upms.interfaces.dto.RoleMenuDtlDTO;
import com.ticho.upms.interfaces.query.RoleDtlQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户信息feign接口
 *
 * @author zhajianjun
 * @date 2022-09-22 14:49
 */
@FeignClient(value = "ticho-upms", contextId = "RoleService", path = "role")
public interface RoleProvider {

    /**
     * 角色菜单查询
     *
     * @param roleDtlQuery 角色菜单查询条件
     * @return {@link Result}<{@link RoleMenuDtlDTO}>
     */
    @PostMapping("listByCodes")
    Result<RoleMenuDtlDTO> listByCodes(@RequestBody RoleDtlQuery roleDtlQuery);

    /**
     * 角色菜单查询
     *
     * @param roleDtlQuery 角色菜单查询条件
     * @return {@link Result}<{@link RoleMenuDtlDTO}>
     */
    @PostMapping("listByIds")
    Result<RoleMenuDtlDTO> listByIds(@RequestBody RoleDtlQuery roleDtlQuery);

}

