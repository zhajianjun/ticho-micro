package com.ticho.auth.controller;


import com.ticho.auth.service.UserService;
import com.ticho.auth.util.OAuth2AccessToken;
import com.ticho.boot.web.annotation.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project: security_jwt
 * @Author: AdoroTutto
 * @Date: 2019-07-14 9:45
 * @Description: 用户表 前端控制器
 */
@RestController
@RequestMapping("/auth")
@View
public class UserController {
    @Autowired
    public UserService userService;

    @View(ignore = true)
    @PostMapping("login")
    public OAuth2AccessToken login(String username, String password) {
        return userService.login(username, password);
    }

}
