package com.ticho.storage;

import com.ticho.feign.auth.service.AuthServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author AdoroTutto
 * @date 2021-10-06 11:49 下午
 */
@RestController
@RequestMapping
@RefreshScope
public class TestController {

    @Autowired
    private AuthServerService authServerService;

    @GetMapping("test")
    public String service1() {
        return authServerService.test();
    }
}
