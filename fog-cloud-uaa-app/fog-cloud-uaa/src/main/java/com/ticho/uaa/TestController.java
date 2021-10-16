package com.ticho.uaa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AdoroTutto
 * @date 2021-10-06 11:49 下午
 */
@RestController
@RequestMapping
@RefreshScope
public class TestController {
    @Value("${test:none}")
    private String value;

    @GetMapping("test")
    public Object test() {
        return value;
    }
}
