package com.ticho.storage.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.web.annotation.View;
import com.ticho.storage.dto.FileInfoDTO;
import com.ticho.storage.dto.FileInfoReqDTO;
import com.ticho.storage.service.FileInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 文件表 控制器
 *
 * @author zhajianjun
 * @date 2021-10-21 23:47
 */
@RestController
@RequestMapping("file")
@Api(tags = "文件操作")
@ApiSort(20)
@View
public class FileInfoController {

    // @formatter:off

    @Autowired
    private FileInfoService fileInfoService;

    @ApiOperation(value = "文件上传")
    @ApiOperationSupport(order = 10)
    @PostMapping("upload")
    public FileInfoDTO upload(FileInfoReqDTO fileInfoReqDTO) {
        return fileInfoService.upload(fileInfoReqDTO);
    }

    @ApiOperation(value = "文件下载", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiOperationSupport(order = 20)
    @ApiImplicitParams({
        @ApiImplicitParam(value = "存储桶名称", name = "bucketName", example = "test"),
        @ApiImplicitParam(value = "资源id", name = "storageId", required = true)
    })
    @GetMapping("download")
    public void download(String bucketName,String storageId) {
        fileInfoService.download(bucketName,storageId);
    }

    @ApiOperation(value = "根据id删除文件")
    @ApiOperationSupport(order = 30)
    @ApiImplicitParams({
        @ApiImplicitParam(value = "存储桶名称", name = "bucketName", example = "test"),
        @ApiImplicitParam(value = "资源id", name = "storageId", required = true)
    })
    @DeleteMapping
    public void delete(String bucketName,String storageId) {
        fileInfoService.delete(bucketName,storageId);
    }

    @ApiOperation(value = "根据资源id获取下载链接")
    @ApiOperationSupport(order = 40)
    @ApiImplicitParams({
        @ApiImplicitParam(value = "存储桶名称", name = "bucketName", example = "test"),
        @ApiImplicitParam(value = "资源id", name = "storageId", required = true),
        @ApiImplicitParam(value = "过期时间 <=7天，默认30分钟，单位：秒", name = "expires")
    })
    @GetMapping("getUrl")
    public String getUrl(String bucketName,String storageId, Integer expires) {
        return fileInfoService.getUrl(bucketName, storageId, expires);
    }


}
