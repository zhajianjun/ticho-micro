package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.security.annotation.IgnoreJwtCheck;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.core.Result;
import com.ticho.upms.application.service.RoleService;
import com.ticho.upms.interfaces.api.RoleProvider;
import com.ticho.upms.interfaces.dto.RoleDTO;
import com.ticho.upms.interfaces.dto.RoleMenuDTO;
import com.ticho.upms.interfaces.dto.RoleMenuDtlDTO;
import com.ticho.upms.interfaces.query.RoleDtlQuery;
import com.ticho.upms.interfaces.query.RoleQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * 角色信息 控制器
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@RestController
@RequestMapping("role")
@Api(tags = "角色信息")
@ApiSort(40)
public class RoleController implements RoleProvider {

    @Autowired
    private RoleService roleService;

    @PreAuthorize("@perm.hasPerms('upms:role:save')")
    @ApiOperation(value = "保存角色信息")
    @ApiOperationSupport(order = 10)
    @PostMapping
    public Result<Void> save(@RequestBody RoleDTO roleDTO) {
        roleService.save(roleDTO);
        return Result.ok();
    }

    @PreAuthorize("@perm.hasPerms('upms:role:remove')")
    @ApiOperation(value = "删除角色信息")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping
    public Result<Void> remove(@RequestParam("id") Long id) {
        roleService.removeById(id);
        return Result.ok();
    }

    @PreAuthorize("@perm.hasPerms('upms:role:update')")
    @ApiOperation(value = "修改角色信息")
    @ApiOperationSupport(order = 30)
    @PutMapping
    public Result<Void> update(@RequestBody RoleDTO roleDTO) {
        roleService.updateById(roleDTO);
        return Result.ok();
    }

    @PreAuthorize("@perm.hasPerms('upms:role:get')")
    @ApiOperation(value = "主键查询角色信息")
    @ApiOperationSupport(order = 40)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping
    public Result<RoleDTO> get(@RequestParam("id") Serializable id) {
        return Result.ok(roleService.getById(id));
    }

    @PreAuthorize("@perm.hasPerms('upms:role:page')")
    @ApiOperation(value = "分页查询角色信息")
    @ApiOperationSupport(order = 50)
    @GetMapping("page")
    public Result<PageResult<RoleDTO>> page(RoleQuery query) {
        return Result.ok(roleService.page(query));
    }

    @PreAuthorize("@perm.hasPerms('upms:role:bindMenu')")
    @ApiOperation(value = "角色绑定菜单信息")
    @ApiOperationSupport(order = 60)
    @PostMapping("bindMenu")
    public Result<Void> bindMenu(@RequestBody RoleMenuDTO roleMenuDTO) {
        roleService.bindMenu(roleMenuDTO);
        return Result.ok();
    }

    @IgnoreJwtCheck
    @ApiOperation(value = "根据角色code查询角色菜单信息")
    @ApiOperationSupport(order = 70)
    @PostMapping("listByCodes")
    public Result<RoleMenuDtlDTO> listByCodes(@RequestBody RoleDtlQuery roleDtlQuery) {
        return Result.ok(roleService.listByCodes(roleDtlQuery));
    }

    @IgnoreJwtCheck
    @ApiOperation(value = "根据角色id查询角色菜单信息")
    @ApiOperationSupport(order = 80)
    @PostMapping("listByIds")
    public Result<RoleMenuDtlDTO> listByIds(@RequestBody RoleDtlQuery roleDtlQuery) {
        return Result.ok(roleService.listByIds(roleDtlQuery));
    }

}
