package com.ticho.auth;

import com.ticho.boot.security.annotation.EnableOauth2AuthServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-21 15:43
 */
@EnableOauth2AuthServer
@EnableFeignClients(basePackages = "com.ticho")
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
