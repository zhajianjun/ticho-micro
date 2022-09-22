package com.ticho.auth.controller;


import com.ticho.auth.dto.LoginDto;
import com.ticho.auth.service.AuthService;
import com.ticho.auth.util.OAuth2AccessToken;
import com.ticho.boot.web.annotation.View;
import io.swagger.annotations.Api;
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
@Api(tags = "登录")
@View
public class AuthController {
    @Autowired
    public AuthService authService;

    @View(ignore = true)
    @PostMapping("login")
    public OAuth2AccessToken login(LoginDto loginDto) {
        return authService.login(loginDto);
    }

}
