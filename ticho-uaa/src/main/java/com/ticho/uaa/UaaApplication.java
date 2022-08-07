package com.ticho.uaa;

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
@MapperScan(basePackages = "com.ticho.uaa")
public class UaaApplication {
    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }
}
