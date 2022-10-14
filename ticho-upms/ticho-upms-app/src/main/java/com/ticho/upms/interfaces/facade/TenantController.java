package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.core.Result;
import com.ticho.upms.application.service.TenantService;
import com.ticho.upms.interfaces.dto.TenantDTO;
import com.ticho.upms.interfaces.dto.TenantSignUpDTO;
import com.ticho.upms.interfaces.query.TenantQuery;
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
 * 租户信息 控制器
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@RestController
@RequestMapping("tenant")
@Api(tags = "租户信息")
@ApiSort(10)
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @PreAuthorize("@pm.hasPerms('tenant:signUp')")
    @ApiOperation(value = "租户注册", notes = "注册成功需要超级管理员确认")
    @ApiOperationSupport(order = 10)
    @PostMapping("signUp")
    public Result<Void> signUp(@RequestBody TenantSignUpDTO tenantSignUpDTO) {
        tenantService.signUp(tenantSignUpDTO);
        return Result.ok();
    }

    @PreAuthorize("@pm.hasPerms('tenant:confirm')")
    @ApiOperation(value = "租户注册确认")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(value = "租户id", name = "tenantId", required = true)
    @PutMapping("confirm")
    public Result<Void> confirm(String tenantId) {
        tenantService.confirm(tenantId);
        return Result.ok();
    }

    @PreAuthorize("@pm.hasPerms('tenant:save')")
    @ApiOperation(value = "保存租户信息")
    @ApiOperationSupport(order = 30)
    @PostMapping
    public Result<Void> save(@RequestBody TenantDTO tenantDTO) {
        tenantService.save(tenantDTO);
        return Result.ok();
    }

    @PreAuthorize("@pm.hasPerms('tenant:remove')")
    @ApiOperation(value = "删除租户信息")
    @ApiOperationSupport(order = 40)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping
    public Result<Void> removeById(@RequestParam("id") Serializable id) {
        tenantService.removeById(id);
        return Result.ok();
    }

    @PreAuthorize("@pm.hasPerms('tenant:update')")
    @ApiOperation(value = "修改租户信息")
    @ApiOperationSupport(order = 50)
    @PutMapping
    public Result<Void> update(@RequestBody TenantDTO tenantDTO) {
        tenantService.updateById(tenantDTO);
        return Result.ok();
    }

    @PreAuthorize("@pm.hasPerms('tenant:get')")
    @ApiOperation(value = "主键查询租户信息")
    @ApiOperationSupport(order = 60)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping
    public Result<TenantDTO> getById(@RequestParam("id") Serializable id) {
        return Result.ok(tenantService.getById(id));
    }

    @PreAuthorize("@pm.hasPerms('tenant:page')")
    @ApiOperation(value = "分页查询租户信息")
    @ApiOperationSupport(order = 70)
    @GetMapping("page")
    public Result<PageResult<TenantDTO>> page(TenantQuery query) {
        return Result.ok(tenantService.page(query));
    }

}
