package com.ticho.common.security.config;

import com.ticho.upms.interfaces.api.OauthInterface;
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
    public JwtSigner jwtSigner(OauthInterface oauthInterface) {
        return new JwtSigner(oauthInterface.publicKey().getData());
    }

}
