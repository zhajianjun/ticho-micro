package com.ticho.upms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动器
 *
 * @author zhajianjun
 * @date 2021-10-06 11:10 上午
 */
@SpringBootApplication
@MapperScan(basePackages = "com.ticho.upms.**.mapper")
public class UpmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(UpmsApplication.class, args);
    }
}
