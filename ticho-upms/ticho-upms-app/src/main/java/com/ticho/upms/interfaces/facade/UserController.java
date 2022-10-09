package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.security.annotation.IgnoreAuth;
import com.ticho.boot.view.core.Result;
import com.ticho.boot.web.annotation.View;
import com.ticho.upms.application.service.UserService;
import com.ticho.upms.interfaces.api.UserProvider;
import com.ticho.upms.interfaces.dto.UpmsUserDTO;
import com.ticho.upms.interfaces.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户信息
 *
 * @author zhajianjun
 * @date 2021-10-24 22:12
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户")
@View
@ApiSort(10)
public class UserController implements UserProvider {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "添加用户")
    @ApiOperationSupport(order = 20)
    @PostMapping
    public void save(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
    }

    @ApiOperation(value = "根据用户名查询用户")
    @ApiOperationSupport(order = 40)
    @GetMapping("getByUsername")
    @IgnoreAuth(inner = true)
    public Result<UpmsUserDTO> getByUsername(String username) {
        return Result.ok(userService.getByUsername(username));
    }

}
