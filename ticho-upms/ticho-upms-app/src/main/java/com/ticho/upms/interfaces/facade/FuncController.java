package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.core.Result;
import com.ticho.upms.application.service.FuncService;
import com.ticho.upms.interfaces.dto.FuncDTO;
import com.ticho.upms.interfaces.query.FuncQuery;
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
 * 功能号信息 控制器
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@RestController
@RequestMapping("func")
@Api(tags = "功能号信息")
@ApiSort(60)
public class FuncController {

    @Autowired
    private FuncService funcService;

    @ApiOperation(value = "保存功能号信息")
    @ApiOperationSupport(order = 10)
    @PostMapping
    public Result<Void> save(@RequestBody FuncDTO funcDTO) {
        funcService.save(funcDTO);
        return Result.ok();
    }

    @ApiOperation(value = "删除功能号信息")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping
    public Result<Void> removeById(@RequestParam("id") Serializable id) {
        funcService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "修改功能号信息")
    @ApiOperationSupport(order = 30)
    @PutMapping
    public Result<Void> update(@RequestBody FuncDTO funcDTO) {
        funcService.updateById(funcDTO);
        return Result.ok();
    }

    @ApiOperation(value = "主键查询功能号信息")
    @ApiOperationSupport(order = 40)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping
    public Result<FuncDTO> getById(@RequestParam("id") Serializable id) {
        return Result.ok(funcService.getById(id));
    }

    @ApiOperation(value = "分页查询功能号信息")
    @ApiOperationSupport(order = 50)
    @GetMapping("page")
    public Result<PageResult<FuncDTO>> page(FuncQuery query) {
        return Result.ok(funcService.page(query));
    }

}
