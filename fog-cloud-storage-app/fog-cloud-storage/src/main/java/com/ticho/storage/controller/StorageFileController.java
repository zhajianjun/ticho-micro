package com.ticho.storage.controller;

import com.github.pagehelper.PageInfo;
import com.ticho.core.mvc.annotation.View;
import com.ticho.storage.entity.StorageFile;
import com.ticho.storage.service.StorageFileService;
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
 * 文件表 控制器
 *
 * @author AdoroTutto
 * @date 2021-10-17 23:59
 */
@RestController
@RequestMapping("storageFile")
@Api(tags = "文件表")
@View
public class StorageFileController {
    @Autowired
    private StorageFileService storageFileService;

    @ApiOperation(value = "添加文件表", position = 10)
    @PostMapping
    public StorageFile save(@RequestBody StorageFile storageFile) {
        storageFileService.save(storageFile);
        return storageFile;
    }

    @ApiOperation(value = "根据id删除文件表", position = 20)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping("{id}")
    public boolean removeById(@PathVariable("id") Serializable id) {
        return storageFileService.removeById(id);
    }

    @ApiOperation(value = "更新文件表", position = 30)
    @PutMapping
    public boolean modify(@RequestBody StorageFile storageFile) {
        return storageFileService.updateById(storageFile);
    }

    @ApiOperation(value = "根据id查询文件表", position = 40)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping("{id}")
    public StorageFile getById(@PathVariable("id") Serializable id) {
        return storageFileService.getById(id);
    }

    @ApiOperation(value = "根据条件查询文件表列表", position = 50)
    @GetMapping("list")
    public List<StorageFile> list(StorageFile storageFile) {
        return storageFileService.list(storageFile);
    }

    @ApiOperation(value = "分页查询文件表列表", position = 60)
    @GetMapping("page")
    public PageInfo<StorageFile> page(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,StorageFile storageFile) {
        return storageFileService.page(pageNum, pageSize, storageFile);
    }
}
