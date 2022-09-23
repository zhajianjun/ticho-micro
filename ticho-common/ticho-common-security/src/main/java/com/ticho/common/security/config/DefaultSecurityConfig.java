package com.ticho.common.security.config;

import com.ticho.common.security.convert.JwtConverter;
import com.ticho.common.security.service.DefaultJwtExtInfo;
import com.ticho.common.security.service.JwtExtInfo;
import com.ticho.common.security.service.LoginUserContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 默认security配置
 *
 * @author zhajianjun
 * @date 2022-09-22 10:32
 */
@Configuration
public class DefaultSecurityConfig {

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(JwtConverter.class)
    public JwtConverter jwtConverter() {
        return new JwtConverter("demo");
    }

    @Bean
    public JwtExtInfo jwtExtInfo() {
        return new DefaultJwtExtInfo();
    }

    @Bean
    public LoginUserContext loginUserContext() {
        return new LoginUserContext();
    }

    @Bean
    public UserDetailsChecker userDetailsChecker() {
        return new AccountStatusUserDetailsChecker();
    }


}
