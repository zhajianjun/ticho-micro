package com.ticho.feign.auth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author AdoroTutto
 * @date 2021-10-11 23:33
 */
@FeignClient("AuthService")
public interface AuthServerService {

    /**
     * Test
     * @return String
     */
    @GetMapping("/test")
    String test();
}
