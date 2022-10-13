package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.core.Result;
import com.ticho.upms.application.service.RoleService;
import com.ticho.upms.interfaces.dto.RoleDTO;
import com.ticho.upms.interfaces.query.RoleQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "保存角色信息")
    @ApiOperationSupport(order = 10)
    @PostMapping
    public Result<Void> save(@RequestBody RoleDTO roleDTO) {
        roleService.save(roleDTO);
        return Result.ok();
    }

    @ApiOperation(value = "删除角色信息")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping
    public Result<Void> removeById(@RequestParam("id") Serializable id) {
        roleService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "修改角色信息")
    @ApiOperationSupport(order = 30)
    @PutMapping
    public Result<Void> update(@RequestBody RoleDTO roleDTO) {
        roleService.updateById(roleDTO);
        return Result.ok();
    }

    @ApiOperation(value = "主键查询角色信息")
    @ApiOperationSupport(order = 40)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping
    public Result<RoleDTO> getById(@RequestParam("id") Serializable id) {
        return Result.ok(roleService.getById(id));
    }

    @ApiOperation(value = "分页查询角色信息")
    @ApiOperationSupport(order = 50)
    @GetMapping("page")
    public Result<PageResult<RoleDTO>> page(RoleQuery query) {
        return Result.ok(roleService.page(query));
    }

}
