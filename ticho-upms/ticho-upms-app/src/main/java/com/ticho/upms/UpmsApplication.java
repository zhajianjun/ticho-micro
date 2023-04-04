package com.ticho.upms;

import com.ticho.boot.security.annotation.EnableOauth2AuthServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动器
 *
 * @author zhajianjun
 * @date 2021-10-06 11:10 上午
 */
@SpringBootApplication
@MapperScan(basePackages = "com.ticho.upms.**.mapper")
@EnableFeignClients(basePackages = "com.ticho")
@EnableOauth2AuthServer
public class UpmsApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(UpmsApplication.class);
        springApplication.setAllowBeanDefinitionOverriding(true);
        springApplication.run(args);
    }

}

