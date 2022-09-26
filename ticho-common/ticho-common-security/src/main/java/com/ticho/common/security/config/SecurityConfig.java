package com.ticho.common.security.config;

import com.ticho.auth.api.AuthBizFeignService;
import com.ticho.boot.security.handle.jwt.JwtConverter;
import com.ticho.boot.security.handle.jwt.JwtEncode;
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
    @ConditionalOnMissingBean(JwtEncode.class)
    public JwtConverter jwtConverter(AuthBizFeignService authBizFeignService) {
        return new JwtConverter(authBizFeignService.publicKey().getData());
    }

}
