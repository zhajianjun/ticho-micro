package com.ticho.storage.prop;

import com.ticho.storage.constant.MinioConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Miniio连接对象
 *
 * @author AdoroTutto
 * @date 2021-10-17 15:48
 */
@ConfigurationProperties(prefix = MinioConstant.MINIO_PREFIX)
@Component
@Data
public class MinioProperty {

    /**
     * minio的地址
     */
    private String endpoint;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 图片大小限制
     */
    private Long imgSize;

    /**
     * 文件大小限制
     */
    private Long fileSize;

}
