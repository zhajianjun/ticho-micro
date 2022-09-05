package com.ticho.uaa;

import com.ticho.boot.swagger.annotation.EnableSwaggerSort;
import com.ticho.boot.web.annotation.EnableTichoMvc;
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
@EnableTichoMvc
@EnableSwaggerSort
@MapperScan(basePackages = "com.ticho.uaa.**.mapper")
public class UaaApplication {
    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }
}
