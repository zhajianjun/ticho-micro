package com.ticho.uaa.security.prop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * securiryt 环境参数
 *
 * @author zhajianjun
 * @date 2022-09-07 17:41
 */
@Component
@ConfigurationProperties(prefix = "ticho.uaa")
@Data
public class SecurityProperty {

    @ApiModelProperty(value = "放行的url,必须/开头", position = 10)
    private String[] antPatterns;

}
