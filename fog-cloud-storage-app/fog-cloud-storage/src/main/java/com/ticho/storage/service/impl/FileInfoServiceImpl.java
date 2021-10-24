package com.ticho.storage.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.ticho.core.mvc.exception.ServiceException;
import com.ticho.core.mvc.util.Assert;
import com.ticho.core.mvc.util.SnowFlakeUtil;
import com.ticho.core.mvc.view.BaseResultCode;
import com.ticho.storage.dto.FileInfoDTO;
import com.ticho.storage.dto.FileInfoReqDTO;
import com.ticho.storage.prop.MinioProperty;
import com.ticho.storage.service.FileInfoService;
import com.ticho.storage.util.MinioTemplate;
import com.ticho.storage.view.MinioResultCode;
import io.minio.GetObjectResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 文件表 服务实现
 *
 * @author AdoroTutto
 * @date 2021-10-21 23:47
 */
@Service
@Slf4j
public class FileInfoServiceImpl implements FileInfoService {
    public static final String X_AMZ_META_PREFIX = "x-amz-meta-";
    public static final String FILENAME_KEY = "filename";
    public static final String REMARK_KEY = "remark";


    @Autowired
    private MinioProperty minioProperty;

    //// @Autowired
    //// private BucketInfoService bucketInfoService;

    @Autowired
    private MinioTemplate minioTemplate;

    @Resource
    private HttpServletResponse response;

    @Override
    public FileInfoDTO upload(FileInfoReqDTO fileInfoReqDTO) {
        String bucketName = fileInfoReqDTO.getBucketName();
        String fileName = fileInfoReqDTO.getFileName();
        String remark = fileInfoReqDTO.getRemark();
        MultipartFile file = fileInfoReqDTO.getFile();
        String originalFilename = file.getOriginalFilename();
        DataSize fileSize = minioProperty.getFileSize();
        Assert.isTrue(file.getSize() <= fileSize.toBytes(), MinioResultCode.FILE_SIZE_TO_LARGER,
                "文件大小不能超出" + fileSize.toMegabytes() + "MB");
        // 后缀名 .png
        String extName = StrUtil.DOT + FileNameUtil.extName(originalFilename);
        String objectName = SnowFlakeUtil.generateStrId() + extName;
        bucketName = getBucketName(bucketName);
        if (StrUtil.isNotBlank(fileName)) {
            if (!fileName.endsWith(extName)) {
                throw new ServiceException(BaseResultCode.PARAM_ERROR, "文件名与上传的文件后缀格式不统一！");
            }
        } else {
            fileName = originalFilename;
        }
        // 构建自定义 header
        Map<String, String> userMetadata = new HashMap<>(1);
        userMetadata.put(FILENAME_KEY, fileName);

        if (StrUtil.isNotBlank(remark)) {
            userMetadata.put(REMARK_KEY, remark);
        }
        //// Assert.isTrue(bucketInfoService.bucketExists(bucketName), MinioResultCode.BUCKET_IS_ALREAD_EXISITS);

        minioTemplate.putObject(bucketName, objectName, userMetadata, file);
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
        fileInfoDTO.setStorageId(objectName);
        fileInfoDTO.setFileName(fileName);
        fileInfoDTO.setContentType(file.getContentType());
        fileInfoDTO.setSize(file.getSize() + "B");
        fileInfoDTO.setRemark(remark);
        fileInfoDTO.setBucket(bucketName);
        return fileInfoDTO;
    }

    @Override
    public void delete(String bucketName, String storageId) {
        Assert.isNotBlank(storageId, BaseResultCode.PARAM_ERROR, "资源id不能为空");
        bucketName = getBucketName(bucketName);
        minioTemplate.removeObject(bucketName, storageId);
    }


    @Override
    public void download(String bucketName, String storageId) {
        // @formatter:off
        Assert.isNotBlank(storageId, BaseResultCode.PARAM_ERROR, "资源id不能为空");
        bucketName = getBucketName(bucketName);
        GetObjectResponse in = minioTemplate.getObject(bucketName, storageId);
        FileInfoDTO fileInfoDto = getFileInfoDto(in.headers());
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLUtil.encode(fileInfoDto.getFileName()));
            response.setContentType(fileInfoDto.getContentType());
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            IoUtil.copy(in, outputStream, 1024);
        } catch (Exception e) {
            log.error("文件下载失败，{}", e.getMessage(), e);
            throw new ServiceException(MinioResultCode.DOWNLOAD_ERROR);
        }
        // @formatter:on
    }

    @Override
    public String getUrl(String bucketName, String storageId, Integer expires) {
        Assert.isNotBlank(storageId, BaseResultCode.PARAM_ERROR, "资源id不能为空");
        if (expires != null) {
            Assert.isTrue(expires <= TimeUnit.DAYS.toSeconds(7), BaseResultCode.PARAM_ERROR, "过期时间最长为7天");
        }
        bucketName = getBucketName(bucketName);
        return minioTemplate.getObjectUrl(bucketName, storageId, expires);
    }


    private String getBucketName(String bucketName) {
        return StrUtil.isBlank(bucketName) ? bucketName.trim() : minioProperty.getDefaultBucket();
    }

    /**
     * 从header中获取用户上传的自定义信息
     * @param headers headers
     * @return 自定义headers信息
     */
    private FileInfoDTO getFileInfoDto(Headers headers) {
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
        for (String key : headers.names()) {
            if (key.startsWith(X_AMZ_META_PREFIX)) {
                String substring = key.substring(X_AMZ_META_PREFIX.length());
                if (substring.equalsIgnoreCase(FILENAME_KEY)) {
                    fileInfoDTO.setFileName(headers.get(key));
                }
                if (substring.equalsIgnoreCase(REMARK_KEY)) {
                    fileInfoDTO.setRemark(headers.get(key));
                }
            }
        }
        long size = Optional.ofNullable(headers.get("Content-Length")).map(Long::valueOf).orElse(0L) / 1000;
        fileInfoDTO.setSize(size + "KB");
        fileInfoDTO.setContentType(headers.get("Content-Type"));
        return fileInfoDTO;
    }
}
