package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.security.annotation.IgnoreAuth;
import com.ticho.boot.security.constant.OAuth2Const;
import com.ticho.boot.security.dto.Oauth2AccessToken;
import com.ticho.boot.security.handle.LoginUserHandle;
import com.ticho.boot.view.core.Result;
import com.ticho.boot.web.util.valid.ValidUtil;
import com.ticho.upms.application.service.UserService;
import com.ticho.upms.interfaces.api.OauthProvider;
import com.ticho.upms.interfaces.dto.UserLoginDTO;
import com.ticho.upms.interfaces.dto.UserSignUpDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 权限用户登录
 *
 * @author zhajianjun
 * @date 2022-09-22 15:36
 */
@RestController(OAuth2Const.OAUTH2_CONTROLLER)
@RequestMapping("oauth")
@ApiSort(20)
@Api(tags = "权限登录")
public class OauthController implements OauthProvider {

    @Autowired
    private LoginUserHandle loginUserHandle;

    @Autowired
    private UserService userService;

    @IgnoreAuth
    @ApiOperation("注册")
    @ApiOperationSupport(order = 10)
    @PostMapping("signUp")
    public Result<Void> signUp(@RequestBody UserSignUpDTO userSignUpDTO) {
        userService.signUp(userSignUpDTO);
        return Result.ok();
    }

    @PreAuthorize("@pm.hasPerms('oauth:confirm')")
    @ApiOperation(value = "用户注册确认", notes = "租户隔离")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(value = "账户", name = "username", required = true)
    @PutMapping("confirm")
    public Result<Void> confirm(String username) {
        userService.confirm(username);
        return Result.ok();
    }

    @IgnoreAuth
    @ApiOperation("登录")
    @ApiOperationSupport(order = 30)
    @PostMapping("token")
    public Oauth2AccessToken token(UserLoginDTO userLoginDTO) {
        ValidUtil.valid(userLoginDTO);
        return loginUserHandle.token(userLoginDTO);
    }

    @IgnoreAuth
    @ApiOperation("刷新token")
    @ApiOperationSupport(order = 40)
    @PostMapping("refreshToken")
    public Oauth2AccessToken refreshToken(String refreshToken) {
        return loginUserHandle.refreshToken(refreshToken);
    }

    @ApiOperation(value = "用户信息查询")
    @ApiOperationSupport(order = 50)
    @GetMapping
    public Principal principal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @ApiOperation("获取公钥")
    @ApiOperationSupport(order = 60)
    @GetMapping("publicKey")
    @Override
    public Result<String> publicKey() {
        return Result.ok(loginUserHandle.publicKey());
    }

    @ApiOperation("测试权限")
    @PreAuthorize("@pm.hasPerms('test')")
    @ApiOperationSupport(order = 1000)
    @GetMapping("test")
    public Result<String> test() {
        return Result.ok(loginUserHandle.publicKey());
    }


}
