package com.ticho.uaa;

import com.ticho.core.swagger.annotation.EnableFogSwaggerSecurityConfig;
import com.ticho.core.swagger.annotation.EnableFogSwaggerSortConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动器
 *
 * @author AdoroTutto
 * @date 2021-10-06 11:10 上午
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFogSwaggerSortConfig
@EnableFogSwaggerSecurityConfig
public class UaaApplication {
    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }
}
