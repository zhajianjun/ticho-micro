package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.core.Result;
import com.ticho.upms.application.service.DictService;
import com.ticho.upms.interfaces.dto.DictDTO;
import com.ticho.upms.interfaces.query.DictQuery;
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
 * 数据字典 控制器
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@RestController
@RequestMapping("dict")
@Api(tags = "数据字典")
@ApiSort(80)
public class DictController {

    @Autowired
    private DictService dictService;

    @ApiOperation(value = "保存数据字典")
    @ApiOperationSupport(order = 10)
    @PostMapping
    public Result<Void> save(@RequestBody DictDTO dictDTO) {
        dictService.save(dictDTO);
        return Result.ok();
    }

    @ApiOperation(value = "删除数据字典")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping
    public Result<Void> removeById(@RequestParam("id") Serializable id) {
        dictService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "修改数据字典")
    @ApiOperationSupport(order = 30)
    @PutMapping
    public Result<Void> update(@RequestBody DictDTO dictDTO) {
        dictService.updateById(dictDTO);
        return Result.ok();
    }

    @ApiOperation(value = "主键查询数据字典")
    @ApiOperationSupport(order = 40)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping
    public Result<DictDTO> getById(@RequestParam("id") Serializable id) {
        return Result.ok(dictService.getById(id));
    }

    @ApiOperation(value = "分页查询数据字典")
    @ApiOperationSupport(order = 50)
    @GetMapping("page")
    public Result<PageResult<DictDTO>> page(DictQuery query) {
        return Result.ok(dictService.page(query));
    }

}
