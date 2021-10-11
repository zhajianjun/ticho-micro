package com.ticho.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
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

    //@Bean
    //@LoadBalanced
    //RestTemplate restTemplate() {
    //    return new RestTemplate();
    //}

    //@Autowired
    //private RestTemplate restTemplate;

    @GetMapping("test")
    public String service1() {
        return new RestTemplate().getForObject("http://localhost:8010/test", String.class);
    }
}
