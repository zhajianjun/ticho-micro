package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.security.annotation.IgnoreJwtCheck;
import com.ticho.boot.security.constant.BaseOAuth2Const;
import com.ticho.boot.security.dto.Oauth2AccessToken;
import com.ticho.boot.security.handle.LoginUserHandle;
import com.ticho.boot.view.core.Result;
import com.ticho.boot.web.annotation.View;
import com.ticho.boot.web.util.valid.ValidUtil;
import com.ticho.upms.application.service.UserService;
import com.ticho.upms.interfaces.api.OauthProvider;
import com.ticho.upms.interfaces.dto.UserLoginDTO;
import com.ticho.upms.interfaces.dto.UserSignUpDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RestController(BaseOAuth2Const.OAUTH2_CONTROLLER)
@RequestMapping("oauth")
@ApiSort(20)
@Api(tags = "权限登录")
public class OauthController implements OauthProvider {

    @Autowired
    private LoginUserHandle loginUserHandle;

    @Autowired
    private UserService userService;

    @IgnoreJwtCheck
    @ApiOperation("注册")
    @ApiOperationSupport(order = 10)
    @PostMapping("signUp")
    public Result<Void> signUp(@RequestBody UserSignUpDTO userSignUpDTO) {
        userService.signUp(userSignUpDTO);
        return Result.ok();
    }

    @PreAuthorize("@perm.hasPerms('upms:oauth:confirm')")
    @ApiOperation(value = "用户注册确认", notes = "租户隔离")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(value = "账户", name = "username", required = true)
    @PutMapping("confirm")
    public Result<Void> confirm(String username) {
        userService.confirm(username);
        return Result.ok();
    }

    @IgnoreJwtCheck
    @ApiOperation("登录")
    @ApiOperationSupport(order = 30)
    @PostMapping("token")
    public Result<Oauth2AccessToken> token(UserLoginDTO userLoginDTO) {
        ValidUtil.valid(userLoginDTO);
        return Result.ok(loginUserHandle.token(userLoginDTO));
    }

    @IgnoreJwtCheck
    @View(ignore = true)
    @ApiOperation(value = "登录验证码", notes = "登录验证码", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiOperationSupport(order = 80)
    @GetMapping("imgcode")
    public void setCode() {
        userService.verifyByCode();
    }

    @IgnoreJwtCheck
    @ApiOperation("刷新token")
    @ApiOperationSupport(order = 40)
    @PostMapping("refreshToken")
    public Result<Oauth2AccessToken> refreshToken(String refreshToken) {
        return Result.ok(loginUserHandle.refreshToken(refreshToken));
    }

    @ApiOperation(value = "token信息查询")
    @ApiOperationSupport(order = 50)
    @GetMapping
    public Result<Principal> principal() {
        return Result.ok(SecurityContextHolder.getContext().getAuthentication());
    }

    @IgnoreJwtCheck(inner = true)
    @ApiOperation("获取公钥")
    @ApiOperationSupport(order = 60)
    @GetMapping("publicKey")
    @Override
    public Result<String> publicKey() {
        return Result.ok(loginUserHandle.publicKey());
    }

}
