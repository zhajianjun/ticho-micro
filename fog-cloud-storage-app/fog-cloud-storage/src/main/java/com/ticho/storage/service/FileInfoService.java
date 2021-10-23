package com.ticho.storage.service;

import com.ticho.storage.dto.FileInfoDTO;
import com.ticho.storage.dto.FileInfoReqDTO;

/**
 * 文件表 服务接口
 *
 * @author AdoroTutto
 * @date 2021-10-21 23:47
 */
public interface FileInfoService {


    /**
     * 文件上传
     *
     * @param fileInfoReqDTO 文件上传信息
     * @return FileInfoDTO
     */
    FileInfoDTO upload(FileInfoReqDTO fileInfoReqDTO);


    /**
     * 根据资源id删除
     *
     * @param bucketName  存储桶名称
     * @param storageId 资源id
     */
    void delete(String bucketName, String storageId);

    /**
     * 根据资源id下载
     *
     * @param bucketName  存储桶名称
     * @param storageId 资源id
     */
    void download(String bucketName, String storageId);

    /**
     * 根据资源id获取下载链接
     *
     * @param bucketName  存储桶名称
     * @param storageId 资源id
     * @param expires 过期时间 <=7天，默认30分钟，单位：秒
     * @return 资源链接
     */
    String getUrl(String bucketName, String storageId, Integer expires);

}

