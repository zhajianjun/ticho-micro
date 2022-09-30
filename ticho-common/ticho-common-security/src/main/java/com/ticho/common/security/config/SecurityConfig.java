package com.ticho.common.security.config;

import com.ticho.upms.api.OauthBizFeignService;
import com.ticho.boot.security.handle.jwt.JwtSigner;
import com.ticho.boot.security.prop.TichoOauthProperty;
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
    @ConditionalOnMissingBean(TichoOauthProperty.class)
    public JwtSigner jwtSigner(OauthBizFeignService oauthBizFeignService) {
        return new JwtSigner(oauthBizFeignService.publicKey().getData());
    }

}