package com.ticho.common.security.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.web.annotation.View;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.Ordered;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 15:36
 */

@RestController
@RequestMapping("principal")
@View
@ApiSort(Ordered.HIGHEST_PRECEDENCE + 100)
@Api(tags = "登录用户信息")
public class PrinciplController {

    @ApiOperation(value = "权限用户信息", notes = "权限用户信息")
    @ApiOperationSupport(order = 10)
    @GetMapping
    @View(ignore = true)
    public Principal principal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
