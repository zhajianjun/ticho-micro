package com.ticho.storage.util;

import com.ticho.mvc.exception.ServiceException;
import com.ticho.storage.prop.MinioProperty;
import com.ticho.storage.view.MinioResultCode;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.Data;
import lombok.SneakyThrows;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Minio文件下载,https://docs.minio.io/cn/java-client-api-reference.html#removeObject
 *
 * @author AdoroTutto
 * @date 2021-10-17 15:48
 */
@SuppressWarnings("all")
@Component
@Data
public class MinioTemplate implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(MinioTemplate.class);

    // @formatter:off

    @Autowired
    private MinioProperty minioProperty;

    public MinioClient client;

    @Override
    public void afterPropertiesSet() {
        this.client = MinioClient
                .builder()
                .credentials(minioProperty.getAccessKey(), minioProperty.getSecretKey())
                .endpoint(minioProperty.getEndpoint())
                .build();
    }

    /**
     * 查询文件存储桶是否存在
     * @param bucketName bucketName
     * @return true-存在，false-不存在
     */
    public boolean bucketExists(String bucketName) {
        try {
            return client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("查询文件存储桶是否存在异常，异常:{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.SELECT_BUCKET_ERROR);
        }
    }

    /**
     * 创建bucket
     * @param bucketName bucketName
     */
    public void createBucket(String bucketName) {
        try {
            if(this.bucketExists(bucketName)){
                return;
            }
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("创建bucket异常，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.CREATE_BUCKET_ERROR);
        }
    }

    /**
     * 根据bucketName删除信息
     * @param bucketName bucket名称
     */
    public void removeBucket(String bucketName) {
        try {
            client.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("获取全部bucket异常，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.DELETE_BUCKET_ERROR);
        }
    }

    /**
     * 根据bucketName获取信息
     * @param bucketName bucket名称
     */
    public Bucket getBucket(String bucketName) {
        return this.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findAny().orElse(null);
    }

    /**
     * 获取全部bucket
     */
    public List<Bucket> listBuckets() {
        try {
            return client.listBuckets();
        } catch (Exception e) {
            log.error("获取全部bucket异常，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.SELECT_BUCKET_ERROR);
        }
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param userMetadata 用户自定义数据
     * @param stream     文件流
     */
    public void putObject(String bucketName, String objectName, String contentType, Map<String, String> userMetadata, InputStream stream) {
        try {
            client.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .userMetadata(userMetadata)
                // 分段上传中允许的最小分段大小为5MiB。
                .stream(stream, stream.available(), 5242880L).contentType(contentType)
                .build());
        } catch (Exception e) {
            log.error("上传文件异常，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.UPLOAD_ERROR);
        }
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param userMetadata 用户自定义数据
     * @param multipartFile  文件
     */
    public void putObject(String bucketName, String objectName, Map<String, String> userMetadata, MultipartFile multipartFile) {
        try {
            InputStream stream = multipartFile.getInputStream();
            client.putObject(
                PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .userMetadata(userMetadata)
                // 分段上传中允许的最小分段大小为5MiB。
                .stream(stream, stream.available(), 5242880L)
                .contentType(multipartFile.getContentType()).build()
            );
        } catch (Exception e) {
            log.error("上传文件异常，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.UPLOAD_ERROR);
        }
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     */
    public void removeObject(String bucketName, String objectName) {
        try {
            client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.error("删除文件异常，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.DELETE_OBJECT_ERROR);
        }
    }

    /**
     * 文件下载
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    public GetObjectResponse getObject(String bucketName, String objectName) {
        try {
            return client.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.error("下载文件异常，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.DOWNLOAD_ERROR);
        }
    }

    /**
     * 文件下载
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param offset 起始字节的位置
     * @param length 要读取的长度 (可选，如果无值则代表读到文件结尾)
     * @return 二进制流
     */
    public GetObjectResponse getObject(String bucketName, String objectName, Long offset, Long length) {
        try {
            return client.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .offset(offset)
                .length(length)
                .build());
        } catch (Exception e) {
            log.error("下载文件异常，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.DOWNLOAD_ERROR);
        }
    }

    /**
     * 获取文件信息
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 文件对象信息
     */
    public StatObjectResponse getObjectInfo(String bucketName, String objectName) {
        try {
            return client.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            if (e instanceof ErrorResponseException) {
                ErrorResponseException e1 = (ErrorResponseException) e;
                Response response = e1.response();
                int code = response.code();
                if (HttpStatus.NOT_FOUND.value() == code) {
                    log.warn("bucket={},object={}文件信息不存在", "default", "1.jpg");
                    return null;
                }
            }
            log.error("查询文件信息，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.DOWNLOAD_ERROR);
        }
    }


    /**
     * 根据文件前缀查询文件
     *
     * @param bucketName bucket名称
     * @param prefix     前缀
     * @param recursive  是否递归查询,true-则查询文件，包含文件夹 false-查询所有文件，不包含文件夹
     * @return MinioItem 列表
     */
    public List<Result<Item>> listObjects(String bucketName, String prefix, boolean recursive) {
        List<Result<Item>> objectList = new ArrayList<>();
        try {
            Iterable<Result<Item>> objectsIterator = client.listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .prefix(prefix)
                .recursive(recursive)
                .build());
            for (Result<Item> result : objectsIterator) {
                objectList.add(result);
            }
        } catch (Exception e) {
            log.error("根据文件前缀查询文件信息异常，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.SELECT_OBJECT_ERROR);
        }
        return objectList;
    }

    /**
     * 获取文件外链
     * @param bucketName bucketName
     * @param objectName 文件名称
     * @param expires 过期时间 <=7天，默认30分钟
     * @return String
     */
    public String getObjectUrl(String bucketName, String objectName, Integer expires) {
        try {
            TimeUnit timeUnit = TimeUnit.SECONDS;
            if(Objects.isNull(expires)){
                expires = 30;
                timeUnit = TimeUnit.MINUTES;
            }
            GetPresignedObjectUrlArgs urlArgs = GetPresignedObjectUrlArgs.builder()
                 .method(Method.GET)
                 .bucket(bucketName)
                 .object(objectName)
                 .expiry(expires, timeUnit)
                 .build();
            return client.getPresignedObjectUrl(urlArgs);
        } catch (Exception e) {
            log.error("获取文件外链异常，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.SELECT_OBJECT_URL_ERROR);
        }
    }

    /**
     * 获取文件外链
     * @param bucketName bucketName
     * @param objectName 文件名称
     * @param expires 过期时间 <=7天
     * @param timeUnit 时间单位
     * @return String
     */
    @SneakyThrows
    public String getObjectUrl(String bucketName, String objectName, Integer expires, TimeUnit timeUnit) {
        try {
            if(Objects.isNull(expires)){
                expires = 24;
                timeUnit = TimeUnit.HOURS;
            }
            GetPresignedObjectUrlArgs urlArgs = GetPresignedObjectUrlArgs.builder()
                 .method(Method.GET)
                 .bucket(bucketName)
                 .object(objectName)
                 .expiry(expires, timeUnit)
                 .build();
            return client.getPresignedObjectUrl(urlArgs);
        } catch (Exception e) {
            log.error("获取文件外链异常，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.SELECT_OBJECT_URL_ERROR);
        }
    }
}
