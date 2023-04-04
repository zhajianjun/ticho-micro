package com.ticho.common.security.config;

import com.ticho.boot.redis.util.RedisUtil;
import com.ticho.boot.security.auth.PermissionService;
import com.ticho.boot.security.constant.BaseSecurityConst;
import com.ticho.boot.security.handle.jwt.JwtSigner;
import com.ticho.boot.security.prop.BaseOauthProperty;
import com.ticho.common.security.component.CommonPermissionServiceImpl;
import com.ticho.common.security.component.PermRedisCacheEvent;
import com.ticho.common.security.handle.CacheHandle;
import com.ticho.upms.interfaces.api.RoleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-24 15:16:52
 */
@Configuration
@PropertySource(value = "classpath:JasyptConfig.properties")
public class SecurityConfig {

    @Value("${ticho.common.security.publicKey}")
    private String publicKey;

    @Bean
    @ConditionalOnMissingBean(BaseOauthProperty.class)
    public JwtSigner jwtSigner() {
        return new JwtSigner(publicKey);
    }

    @Bean(BaseSecurityConst.PM)
    public PermissionService permissionService(RoleProvider roleProvider) {
        return new CommonPermissionServiceImpl(roleProvider);
    }

    @Bean
    public CacheHandle permHandle(RedisUtil<String, String> redisUtil) {
        return new CacheHandle(redisUtil);
    }

    @Bean
    public PermRedisCacheEvent permRedisCacheEvent(CacheHandle cacheHandle) {
        return new PermRedisCacheEvent(cacheHandle);
    }

}
