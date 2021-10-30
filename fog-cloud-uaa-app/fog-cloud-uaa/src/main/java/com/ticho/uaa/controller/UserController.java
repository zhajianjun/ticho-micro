package com.ticho.uaa.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.core.datasource.view.PageResult;
import com.ticho.core.mvc.annotation.View;
import com.ticho.uaa.entity.User;
import com.ticho.uaa.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.security.Principal;


/**
 * 用户 控制器
 *
 * @author AdoroTutto
 * @date 2021-10-24 22:12
 */
@RestController
@RequestMapping("User")
@Api(tags = "用户")
@View
@ApiSort(10)
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "权限用户信息", notes = "权限用户信息", position = 5)
    @GetMapping("principal")
    @View(ignore = true)
    public Principal principal(Principal principal) {
        return principal;
    }

    @ApiOperation(value = "添加用户", position = 10)
    @PostMapping
    public User save(@RequestBody User user) {
        userService.save(user);
        return user;
    }

    @ApiOperation(value = "根据id删除用户", position = 20)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping("{id}")
    public boolean removeById(@PathVariable("id") Serializable id) {
        return userService.removeById(id);
    }

    @ApiOperation(value = "更新用户", position = 30)
    @PutMapping
    public boolean modify(@RequestBody User user) {
        return userService.updateById(user);
    }

    @ApiOperation(value = "根据id查询用户", position = 40)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping("{id}")
    public User getById(@PathVariable("id") Serializable id) {
        return userService.getById(id);
    }

    @ApiOperation(value = "分页查询用户列表", position = 50)
    @GetMapping("page")
    public PageResult<User> page(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, User user) {
        return userService.page(pageNum, pageSize, user);
    }
}
