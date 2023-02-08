package com.ticho.upms.interfaces.facade;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.ticho.boot.view.core.Result;
import com.ticho.common.security.dto.PermDTO;
import com.ticho.common.security.handle.CacheHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限标识信息 控制器
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@RestController
@RequestMapping("perm")
@Api(tags = "权限标识信息")
@ApiSort(60)
public class PermController {

    @Autowired
    private CacheHandle cacheHandle;

    @PreAuthorize("@perm.hasPerms('upms:perm:list')")
    @ApiOperation(value = "查询所有权限标识信息")
    @ApiOperationSupport(order = 60)
    @GetMapping("list")
    public Result<List<PermDTO>> listAll() {
        return Result.ok(cacheHandle.listAllAppPerms());
    }

}
