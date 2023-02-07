package com.ticho.common.security.config;

import com.ticho.boot.redis.util.RedisUtil;
import com.ticho.boot.security.handle.jwt.JwtSigner;
import com.ticho.boot.security.prop.BaseOauthProperty;
import com.ticho.boot.view.core.Result;
import com.ticho.common.security.component.PermRedisCacheEvent;
import com.ticho.common.security.handle.PermHandle;
import com.ticho.upms.interfaces.api.OauthProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-24 15:16:52
 */
@Configuration
public class SecurityConfig {

    @Bean
    @ConditionalOnMissingBean(BaseOauthProperty.class)
    public JwtSigner jwtSigner(OauthProvider oauthProvider) {
        Result<String> stringResult = oauthProvider.publicKey();
        return new JwtSigner(stringResult.getData());
    }

    @Bean
    public PermHandle permHandle(RedisUtil<String, String> redisUtil) {
        return new PermHandle(redisUtil);
    }

    @Bean
    public PermRedisCacheEvent permRedisCacheEvent(PermHandle permHandle) {
        return new PermRedisCacheEvent(permHandle);
    }

}
