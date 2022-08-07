package com.ticho.storage.service;

import com.ticho.storage.dto.BucketInfoDTO;

import java.util.List;

/**
 * 文件桶 服务接口
 *
 * @author zhajianjun
 * @date 2021-10-21 23:47
 */
public interface BucketInfoService {

    /**
     * 文件桶是否存在
     *
     * @param bucketName 文件桶名称
     * @return true-存在 false-不存在
     */
    boolean bucketExists(String bucketName);

    /**
     * 创建文件桶
     *
     * @param bucketName 文件桶名称
     */
    void createBucket(String bucketName);

    /**
     * 删除文件桶
     *
     * @param bucketName 文件桶名称
     * @param delAllFile 是否删除所有文件
     */
    void removeBucket(String bucketName, boolean delAllFile);

    /**
     * 获取所有的文件桶
     *
     * @return List<BucketInfoDTO>
     */
    List<BucketInfoDTO> listBuckets();

}

