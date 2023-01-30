package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.core.Result;
import com.ticho.upms.application.service.MenuService;
import com.ticho.upms.interfaces.dto.MenuDTO;
import com.ticho.upms.interfaces.dto.MenuFuncDTO;
import com.ticho.upms.interfaces.dto.MenuFuncDtlDTO;
import com.ticho.upms.interfaces.query.MenuQuery;
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

    @ApiOperation(value = "保存菜单信息")
    @ApiOperationSupport(order = 10)
    @PostMapping
    public Result<Void> save(@RequestBody MenuDTO menuDTO) {
        menuService.save(menuDTO);
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单信息")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping
    public Result<Void> removeById(@RequestParam("id") Serializable id) {
        menuService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单信息")
    @ApiOperationSupport(order = 30)
    @PutMapping
    public Result<Void> update(@RequestBody MenuDTO menuDTO) {
        menuService.updateById(menuDTO);
        return Result.ok();
    }

    @ApiOperation(value = "主键查询菜单信息")
    @ApiOperationSupport(order = 40)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping
    public Result<MenuDTO> getById(@RequestParam("id") Serializable id) {
        return Result.ok(menuService.getById(id));
    }

    @ApiOperation(value = "分页查询菜单信息")
    @ApiOperationSupport(order = 50)
    @GetMapping("page")
    public Result<PageResult<MenuDTO>> page(MenuQuery query) {
        return Result.ok(menuService.page(query));
    }

    @ApiOperation(value = "菜单添加功能号")
    @ApiOperationSupport(order = 60)
    @PostMapping("saveFunc")
    public Result<Void> saveFunc(@RequestBody MenuFuncDTO menuFuncDTO) {
        menuService.saveFunc(menuFuncDTO);
        return Result.ok();
    }
    
    @ApiOperation(value = "菜单移除功能号")
    @ApiOperationSupport(order = 70)
    @DeleteMapping("removeFunc")
    public Result<Void> removeFunc(MenuFuncDTO menuFuncDTO) {
        menuService.removeFunc(menuFuncDTO);
        return Result.ok();
    }

    @ApiOperation(value = "获取所有菜单信息")
    @ApiOperationSupport(order = 80)
    @GetMapping("listAll")
    public Result<List<MenuFuncDtlDTO>> listAll(boolean containFunc) {
        return Result.ok(menuService.listAll(containFunc));
    }

}
