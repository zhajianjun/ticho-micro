package com.ticho.upms.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.security.annotation.IgnoreAuth;
import com.ticho.boot.web.annotation.View;
import com.ticho.upms.dto.UserDTO;
import com.ticho.upms.entity.User;
import com.ticho.upms.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户 控制器
 *
 * @author zhajianjun
 * @date 2021-10-24 22:12
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户")
@View
@ApiSort(10)
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "添加用户")
    @ApiOperationSupport(order = 20)
    @PostMapping
    public User save(@RequestBody User user) {
        userService.save(user);
        return user;
    }

    @ApiOperation(value = "根据用户名查询用户")
    @ApiOperationSupport(order = 40)
    @GetMapping("getByUsername")
    @IgnoreAuth(inner = true)
    public UserDTO getByUsername(String username) {
        return userService.getByUsername(username);
    }

}
