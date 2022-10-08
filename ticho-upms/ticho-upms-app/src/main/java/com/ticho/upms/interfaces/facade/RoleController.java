package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.web.annotation.View;
import com.ticho.upms.application.service.RoleService;
import com.ticho.upms.infrastructure.entity.Role;
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
 * @date 2022-10-08 17:45
 */
@RestController
@RequestMapping("role")
@Api(tags = "角色信息")
@View
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "保存角色信息", notes = "保存角色信息")
    @ApiOperationSupport(order = 10)
    @PostMapping
    public void save(@RequestBody RoleDTO roleDTO) {
        roleService.save(roleDTO);
    }

    @ApiOperation(value = "删除角色信息", notes = "根据id删除角色信息")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping
    public void removeById(@RequestParam("id") Serializable id) {
        roleService.removeById(id);
    }

    @ApiOperation(value = "修改角色信息", notes = "根据id修改角色信息")
    @ApiOperationSupport(order = 30)
    @PutMapping
    public void update(@RequestBody RoleDTO roleDTO) {
        roleService.updateById(roleDTO);
    }

    @ApiOperation(value = "角色信息查询", notes = "根据id查询角色信息")
    @ApiOperationSupport(order = 40)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping
    public Role getById(@RequestParam("id") Serializable id) {
        return roleService.getById(id);
    }

    @ApiOperation(value = "角色信息列表查询(分页)", notes = "分页查询角色信息列表")
    @ApiOperationSupport(order = 50)
    @GetMapping("page")
    public PageResult<RoleDTO> page(RoleQuery query) {
        return roleService.page(query);
    }

}
