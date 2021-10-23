package com.ticho.storage.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.core.mvc.annotation.View;
import com.ticho.storage.dto.BucketInfoDTO;
import com.ticho.storage.service.BucketInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 文件桶 控制器
 *
 * @author AdoroTutto
 * @date 2021-10-21 23:47
 */
@RestController
@RequestMapping("bucket")
@Api(tags = "文件桶操作")
@View
@ApiSort(10)
public class BucketInfoController {

    // @formatter:off

    @Autowired
    private BucketInfoService bucketInfoService;

    @ApiOperation(value = "创建文件桶", notes = "创建文件桶", position = 10)
    @ApiImplicitParam(value = "存储桶名称", name = "bucketName", required = true)
    @PostMapping
    public void createBucket(String bucketName) {
        bucketInfoService.createBucket(bucketName);
    }

    @ApiOperation(value = "删除文件桶", notes = "删除文件桶", position = 20)
    @ApiImplicitParams({
        @ApiImplicitParam(value = "存储桶名称", name = "bucketName", required = true),
        @ApiImplicitParam(value = "是否删除所有文件，true-默认删除所有文件和桶，false-桶中有文件不可删除", name = "delAllFile", required = true)
    })
    @DeleteMapping
    public void removeBucket(String bucketName, @RequestParam(defaultValue = "false") boolean delAllFile) {
        bucketInfoService.removeBucket(bucketName, delAllFile);
    }

    @ApiOperation(value = "查询文件桶是否存在", notes = "查询文件桶是否存在", position = 30)
    @ApiImplicitParam(value = "存储桶名称", name = "bucketName", required = true)
    @GetMapping
    public boolean listBuckets(String bucketName) {
        return bucketInfoService.bucketExists(bucketName);
    }

    @ApiOperation(value = "查询所有文件桶", notes = "获取所有文件桶", position = 40)
    @GetMapping("list")
    public List<BucketInfoDTO> listBuckets() {
        return bucketInfoService.listBuckets();
    }

}
