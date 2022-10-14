package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.core.Result;
import com.ticho.upms.application.service.UserService;
import com.ticho.upms.interfaces.api.UserProvider;
import com.ticho.upms.interfaces.dto.UserDTO;
import com.ticho.upms.interfaces.query.UserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * 用户信息 控制器
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户信息")
@ApiSort(30)
public class UserController implements UserProvider {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "保存用户信息")
    @ApiOperationSupport(order = 10)
    @PostMapping
    public Result<Void> save(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
        return Result.ok();
    }

    @ApiOperation(value = "删除用户信息")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @DeleteMapping
    public Result<Void> removeById(@RequestParam("id") Serializable id) {
        userService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "修改用户信息", notes = "无法修改密码")
    @ApiOperationSupport(order = 30)
    @PutMapping
    public Result<Void> update(@RequestBody UserDTO userDTO) {
        userService.updateById(userDTO);
        return Result.ok();
    }

    @ApiOperation(value = "主键查询用户信息")
    @ApiOperationSupport(order = 50)
    @ApiImplicitParam(value = "编号", name = "id", required = true)
    @GetMapping
    public Result<UserDTO> getById(@RequestParam("id") Serializable id) {
        return Result.ok(userService.getById(id));
    }

    @ApiOperation(value = "分页查询用户信息")
    @ApiOperationSupport(order = 60)
    @GetMapping("page")
    public Result<PageResult<UserDTO>> page(UserQuery query) {
        return Result.ok(userService.page(query));
    }

}
