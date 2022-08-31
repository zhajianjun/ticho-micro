package com.ticho.admin;

import com.ticho.boot.web.annotation.EnableTichoMvc;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author zhajianjun
 * @date 2021-10-12 12:57 下午
 */
@SpringBootApplication
@EnableAdminServer
@EnableTichoMvc
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
