package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.core.Result;
import com.ticho.upms.application.service.DictTypeService;
import com.ticho.upms.interfaces.dto.DictTypeDTO;
import com.ticho.upms.interfaces.query.DictTypeQuery;
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
 * 数据字典类型 控制器
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@RestController
@RequestMapping("dictType")
@Api(tags = "数据字典类型")
@ApiSort(70)
public class DictTypeController {

    @Autowired
    private DictTypeService dictTypeService;

    @ApiOperation(value = "保存数据字典类型")
    @ApiOperationSupport(order = 10)
    @PostMapping
    public Result<Void> save(@RequestBody DictTypeDTO dictTypeDTO) {
        dictTypeService.save(dictTypeDTO);
        return Result.ok();
    }

    @ApiOperation(value = "删除数据字典类型")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping
    public Result<Void> removeById(@RequestParam("id") Long id) {
        dictTypeService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "修改数据字典类型")
    @ApiOperationSupport(order = 30)
    @PutMapping
    public Result<Void> update(@RequestBody DictTypeDTO dictTypeDTO) {
        dictTypeService.updateById(dictTypeDTO);
        return Result.ok();
    }

    @ApiOperation(value = "主键查询数据字典类型")
    @ApiOperationSupport(order = 40)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping
    public Result<DictTypeDTO> getById(@RequestParam("id") Long id) {
        return Result.ok(dictTypeService.getById(id));
    }

    @ApiOperation(value = "分页查询数据字典类型")
    @ApiOperationSupport(order = 50)
    @GetMapping("page")
    public Result<PageResult<DictTypeDTO>> page(DictTypeQuery query) {
        return Result.ok(dictTypeService.page(query));
    }

}
