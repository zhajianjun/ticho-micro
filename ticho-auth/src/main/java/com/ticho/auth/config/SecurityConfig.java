package com.ticho.auth.config;

import com.ticho.boot.security.handle.jwt.JwtConverter;
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
    public JwtConverter jwtConverter() {
        return new JwtConverter("rsa_first.jks", "com.ticho", "123456");
    }

}
