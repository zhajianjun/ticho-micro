package com.ticho.storage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ticho.core.mvc.exception.ServiceException;
import com.ticho.core.mvc.util.Assert;
import com.ticho.storage.dto.BucketInfoDTO;
import com.ticho.storage.service.BucketInfoService;
import com.ticho.storage.util.MinioTemplate;
import com.ticho.storage.view.MinioResultCode;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// @formatter:off
/**
 * 文件桶 服务实现
 *
 * @author AdoroTutto
 * @date 2021-10-21 23:47
 */
@Service
@Slf4j
public class BucketInfoServiceImpl implements BucketInfoService {
    // @formatter:on

    @Autowired
    private MinioTemplate minioTemplate;


    @Override
    public boolean bucketExists(String bucketName) {
        if (StrUtil.isBlank(bucketName)) {
            return false;
        }
        return minioTemplate.bucketExists(bucketName);
    }

    @Override
    public void createBucket(String bucketName) {
        Assert.isTrue(!minioTemplate.bucketExists(bucketName), MinioResultCode.BUCKET_IS_ALREAD_EXISITS);
        minioTemplate.createBucket(bucketName);
    }

    @Override
    public void removeBucket(String bucketName, boolean delAllFile) {
        Assert.isTrue(bucketExists(bucketName), MinioResultCode.BUCKET_IS_ALREAD_EXISITS);

        //获取当前桶内所有文件
        List<Result<Item>> allObjects = minioTemplate.listObjects(bucketName, "", true);
        if (!delAllFile) {
            Assert.isTrue(allObjects.isEmpty(), MinioResultCode.BUCKET_IS_NOT_EMPTY);
        }
        try {
            for (Result<Item> itemResult : allObjects) {
                String objectName = itemResult.get().objectName();
                minioTemplate.removeObject(bucketName, objectName);
            }
        } catch (Exception e) {
            log.error("删除文件异常，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.DELETE_OBJECT_ERROR);
        }
        minioTemplate.removeBucket(bucketName);
    }

    @Override
    public List<BucketInfoDTO> listBuckets() {
        return minioTemplate.listBuckets().stream().map(this::getBucketInfoDTO).collect(Collectors.toList());
    }

    private BucketInfoDTO getBucketInfoDTO(Bucket bucketName) {
        if (bucketName == null) {
            return null;
        }
        BucketInfoDTO bucketDto = new BucketInfoDTO();
        bucketDto.setBucket(bucketName.name());
        bucketDto.setCreationDate(bucketName.creationDate());
        return bucketDto;
    }
}
