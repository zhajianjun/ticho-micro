package com.ticho.storage.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.ticho.boot.minio.component.MinioTemplate;
import com.ticho.boot.minio.prop.MinioProperty;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.exception.BizException;
import com.ticho.boot.view.util.Assert;
import com.ticho.boot.web.util.CloudIdUtil;
import com.ticho.storage.dto.FileInfoDTO;
import com.ticho.storage.dto.FileInfoReqDTO;
import com.ticho.storage.service.FileInfoService;
import com.ticho.storage.emums.MioErrCode;
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
 * @author zhajianjun
 * @date 2021-10-21 23:47
 */
@Service
@Slf4j
public class FileInfoServiceImpl implements FileInfoService {
    public static final String X_AMZ_META_PREFIX = "x-amz-meta-";
    public static final String FILENAME_KEY = "fileName";
    public static final String REMARK_KEY = "remark";


    @Autowired
    private MinioProperty minioProperty;

    @Autowired
    private MinioTemplate minioTemplate;

    @Resource
    private HttpServletResponse response;

    @Override
    public FileInfoDTO upload(FileInfoReqDTO fileInfoReqDTO) {
        // @formatter:off
        String bucketName = fileInfoReqDTO.getBucketName();
        String fileName = fileInfoReqDTO.getFileName();
        String remark = fileInfoReqDTO.getRemark();
        MultipartFile file = fileInfoReqDTO.getFile();
        String originalFilename = file.getOriginalFilename();
        DataSize fileSize = minioProperty.getMaxFileSize();
        Assert.isTrue(file.getSize() <= fileSize.toBytes(), MioErrCode.FILE_SIZE_TO_LARGER, "文件大小不能超出" + fileSize.toMegabytes() + "MB");
        // 后缀名 .png
        String extName = StrUtil.DOT + FileNameUtil.extName(originalFilename);
        String objectName = CloudIdUtil.getId() + extName;
        bucketName = getBucketName(bucketName);
        if (StrUtil.isNotBlank(fileName)) {
            if (!fileName.endsWith(extName)) {
                throw new BizException(BizErrCode.PARAM_ERROR, "文件名与上传的文件后缀格式不统一！");
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
        // @formatter:on
    }

    @Override
    public void delete(String bucketName, String storageId) {
        String errMsg = "资源id不能为空";
        Assert.isNotBlank(storageId, BizErrCode.PARAM_ERROR, errMsg);
        bucketName = getBucketName(bucketName);
        minioTemplate.removeObject(bucketName, storageId);
    }


    @Override
    public void download(String bucketName, String storageId) {
        // @formatter:off
        Assert.isNotBlank(storageId, BizErrCode.PARAM_ERROR, "资源id不能为空");
        bucketName = getBucketName(bucketName);
        GetObjectResponse in = minioTemplate.getObject(bucketName, storageId);
        Headers headers = in.headers();
        FileInfoDTO fileInfoDto = getFileInfoDto(headers);
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLUtil.encodeAll(fileInfoDto.getFileName()));
            response.setContentType(fileInfoDto.getContentType());
            response.setHeader(HttpHeaders.PRAGMA, "no-cache");
            response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            response.setHeader(HttpHeaders.CONTENT_LENGTH, headers.get(HttpHeaders.CONTENT_LENGTH));
            response.setDateHeader(HttpHeaders.EXPIRES, 0);
            IoUtil.copy(in, outputStream, 1024);
        } catch (Exception e) {
            log.error("文件下载失败，{}", e.getMessage(), e);
            throw new BizException(MioErrCode.DOWNLOAD_ERROR);
        }
        // @formatter:on
    }

    @Override
    public String getUrl(String bucketName, String storageId, Integer expires) {
        Assert.isNotBlank(storageId, BizErrCode.PARAM_ERROR, "资源id不能为空");
        if (expires != null) {
            Assert.isTrue(expires <= TimeUnit.DAYS.toSeconds(7), BizErrCode.PARAM_ERROR, "过期时间最长为7天");
        }
        bucketName = getBucketName(bucketName);
        return minioTemplate.getObjectUrl(bucketName, storageId, expires);
    }


    private String getBucketName(String bucketName) {
        return StrUtil.isNotBlank(bucketName) ? bucketName.trim() : minioProperty.getDefaultBucket();
    }

    /**
     * 从header中获取用户上传的自定义信息
     *
     * @param headers headers
     * @return 自定义headers信息
     */
    private FileInfoDTO getFileInfoDto(Headers headers) {
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
        for (String key : headers.names()) {
            if (!key.startsWith(X_AMZ_META_PREFIX)) {
                continue;
            }
            String substring = key.substring(X_AMZ_META_PREFIX.length());
            if (substring.equalsIgnoreCase(FILENAME_KEY)) {
                fileInfoDTO.setFileName(headers.get(key));
            }
            if (substring.equalsIgnoreCase(REMARK_KEY)) {
                fileInfoDTO.setRemark(headers.get(key));
            }
        }
        long size = Optional.ofNullable(headers.get(HttpHeaders.CONTENT_LENGTH)).map(Long::valueOf).orElse(0L);
        fileInfoDTO.setSize(size + "B");
        fileInfoDTO.setContentType(headers.get(HttpHeaders.CONTENT_TYPE));
        return fileInfoDTO;
    }
}
