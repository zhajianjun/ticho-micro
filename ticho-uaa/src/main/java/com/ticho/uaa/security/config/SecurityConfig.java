package com.ticho.uaa.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.util.stream.Collectors;


/**
 * WebSecurity
 *
 * @author zhajianjun
 * @date 2020-07-02 12:36
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenEnhancer customTokenExtraInfo() {
        return new CustomTokenExtraInfo();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        // @formatter:off
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 1. 对称加密方式
        // jwtAccessTokenConverter.setSigningKey("secret");
        // 2.非对称加密， 直接使用公钥
        //Resource resource = new ClassPathResource("pub.txt");
        //String key = null;
        //try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
        //    key = reader.lines().collect(Collectors.joining("\n"));
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //jwtAccessTokenConverter.setSigningKey(key);
        // 3.非对称加密，使用证书
        ClassPathResource resource = new ClassPathResource("rsa_first.jks");
        //设置密钥对（私钥） 此处传入的是创建jks文件时的别名-alias 和 秘钥库访问密码
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, "123456".toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("com.ticho");
        jwtAccessTokenConverter.setKeyPair(keyPair);
        // 注入额外信息
        //DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        //defaultAccessTokenConverter.setUserTokenConverter(new CustomTokenExtraInfo());
        //jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
        // @formatter:on
        return jwtAccessTokenConverter;
    }

    /**
     * 默认的bean StrictHttpFirewall 对于特殊url会有RequestRejectedException异常，更改HttpFirewall的bean实现
     */
    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

    /**
     * 自定义bean，去除ROLE_前缀
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        // 自定义登录页面
        http.csrf().disable()
            .httpBasic().disable()
            .formLogin().loginPage("/login.html").loginProcessingUrl("/login");
    }
}
