package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.view.core.Result;
import com.ticho.upms.application.service.MenuService;
import com.ticho.upms.interfaces.dto.MenuDTO;
import com.ticho.upms.interfaces.dto.MenuDtlDTO;
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

import java.util.List;

/**
 * 菜单信息 控制器
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@RestController
@RequestMapping("menu")
@Api(tags = "菜单信息")
@ApiSort(50)
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PreAuthorize("@perm.hasPerms('upms:menu:save')")
    @ApiOperation(value = "保存菜单信息")
    @ApiOperationSupport(order = 10)
    @PostMapping
    public Result<Void> save(@RequestBody MenuDTO menuDTO) {
        menuService.save(menuDTO);
        return Result.ok();
    }

    @PreAuthorize("@perm.hasPerms('upms:menu:remove')")
    @ApiOperation(value = "删除菜单信息")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping
    public Result<Void> remove(@RequestParam("id") Long id) {
        menuService.removeById(id);
        return Result.ok();
    }

    @PreAuthorize("@perm.hasPerms('upms:menu:update')")
    @ApiOperation(value = "修改菜单信息")
    @ApiOperationSupport(order = 30)
    @PutMapping
    public Result<Void> update(@RequestBody MenuDTO menuDTO) {
        menuService.updateById(menuDTO);
        return Result.ok();
    }

    @PreAuthorize("@perm.hasPerms('upms:menu:get')")
    @ApiOperation(value = "主键查询菜单信息")
    @ApiOperationSupport(order = 40)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping
    public Result<MenuDTO> get(@RequestParam("id") Long id) {
        return Result.ok(menuService.getById(id));
    }

    @PreAuthorize("@perm.hasPerms('upms:menu:list')")
    @ApiOperation(value = "查询所有菜单信息")
    @ApiOperationSupport(order = 80)
    @GetMapping("list")
    public Result<List<MenuDtlDTO>> list() {
        return Result.ok(menuService.list());
    }

}
