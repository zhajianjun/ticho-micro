package com.ticho.storage.controller;

import com.github.pagehelper.PageInfo;
import com.ticho.mvc.annotation.View;
import com.ticho.storage.entity.StorageBucket;
import com.ticho.storage.service.StorageBucketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;


/**
 * 文件桶 控制器
 *
 * @author AdoroTutto
 * @date 2021-10-17 23:59
 */
@RestController
@RequestMapping("storageBucket")
@Api(tags = "文件桶")
@View
public class StorageBucketController {
    @Autowired
    private StorageBucketService storageBucketService;

    @ApiOperation(value = "添加文件桶", position = 10)
    @PostMapping
    public StorageBucket save(@RequestBody StorageBucket storageBucket) {
        storageBucketService.save(storageBucket);
        return storageBucket;
    }

    @ApiOperation(value = "根据id删除文件桶", position = 20)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping("{id}")
    public boolean removeById(@PathVariable("id") Serializable id) {
        return storageBucketService.removeById(id);
    }

    @ApiOperation(value = "更新文件桶", position = 30)
    @PutMapping
    public boolean modify(@RequestBody StorageBucket storageBucket) {
        return storageBucketService.updateById(storageBucket);
    }

    @ApiOperation(value = "根据id查询文件桶", position = 40)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping("{id}")
    public StorageBucket getById(@PathVariable("id") Serializable id) {
        return storageBucketService.getById(id);
    }

    @ApiOperation(value = "根据条件查询文件桶列表", position = 50)
    @GetMapping("list")
    public List<StorageBucket> list(StorageBucket storageBucket) {
        return storageBucketService.list(storageBucket);
    }

    @ApiOperation(value = "分页查询文件桶列表", position = 60)
    @GetMapping("page")
    public PageInfo<StorageBucket> page(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,StorageBucket storageBucket) {
        return storageBucketService.page(pageNum, pageSize, storageBucket);
    }
}
