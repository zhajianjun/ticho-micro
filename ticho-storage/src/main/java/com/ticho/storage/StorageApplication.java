package com.ticho.storage;

import com.ticho.boot.web.annotation.EnableTichoMvc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author zhajianjun
 * @date 2021-10-06 11:10 上午
 */
@EnableTichoMvc
@SpringBootApplication
public class StorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }
}
