package com.ticho.upms.infrastructure.core.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author zhajianjun
 * @date 2023-02-07 15:40
 */
@Component
@Data
@ConfigurationProperties(prefix = "ticho.upms")
public class CacheProperty {

    /** 通用过期时间，单位:秒(s) */
    private Long commonExpire = 300L;

    /** 用户过期时间，单位:秒(s) */
    private Long userExpire = 300L;

    /** 角色过期时间，单位:秒(s) */
    private Long roleExpire = 300L;

    /** 菜单过期时间，单位:秒(s) */
    private Long menuExpire = 300L;

}
