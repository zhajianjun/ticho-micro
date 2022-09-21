package com.ticho.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-21 10:21
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("user")
    public Principal principal(Principal principal) {
        return principal;
    }

}
