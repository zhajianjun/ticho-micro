package com.ticho.auth.config;

import com.ticho.boot.security.annotation.EnableOauth2AuthServer;
import com.ticho.boot.security.handle.jwt.JwtSigner;
import com.ticho.boot.security.prop.TichoOauthProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-24 15:16:52
 */
@EnableOauth2AuthServer
@Configuration
public class SecurityConfig {

    @Bean
    @ConditionalOnBean(TichoOauthProperty.class)
    public JwtSigner jwtSigner() {
        return new JwtSigner("rsa_first.jks", "com.ticho", "123456");
    }

}
