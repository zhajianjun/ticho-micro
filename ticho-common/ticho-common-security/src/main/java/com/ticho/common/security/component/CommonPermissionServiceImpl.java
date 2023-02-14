package com.ticho.common.security.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.ticho.boot.security.auth.PermissionService;
import com.ticho.boot.view.core.Result;
import com.ticho.common.security.constant.SecurityConst;
import com.ticho.common.security.dto.SecurityUser;
import com.ticho.common.security.util.UserUtil;
import com.ticho.upms.interfaces.api.RoleProvider;
import com.ticho.upms.interfaces.dto.RoleMenuDtlDTO;
import com.ticho.upms.interfaces.query.RoleDtlQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 接口权限实现
 *
 * @author zhajianjun
 * @date 2022-09-26 17:31:58
 */
@Slf4j
@Order(100)
public class CommonPermissionServiceImpl implements PermissionService {

    private final RoleProvider roleProvider;

    public CommonPermissionServiceImpl(RoleProvider roleProvider) {
        this.roleProvider = roleProvider;
    }

    public boolean hasPerms(String... permissions) {
        log.debug("权限校验，permissions = {}", String.join(",", permissions));
        if (ArrayUtil.isEmpty(permissions)) {
            return false;
        }
        SecurityUser currentUser = UserUtil.getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return false;
        }
        String tenantId = currentUser.getTenantId();
        List<String> roleCodes = currentUser.getRoleCodes();
        if (CollUtil.isEmpty(roleCodes)) {
            return false;
        }
        if (roleCodes.contains(SecurityConst.ADMIN)) {
            return true;
        }
        return hasPerms(tenantId, roleCodes, permissions);
    }

    protected boolean hasPerms(String tenantId, List<String> roleCodes, String[] permissions) {
        RoleDtlQuery roleDtlQuery = new RoleDtlQuery();
        roleDtlQuery.setTenantId(tenantId);
        roleDtlQuery.setRoleCodes(roleCodes);
        Result<RoleMenuDtlDTO> result = roleProvider.listByCodes(roleDtlQuery);
        RoleMenuDtlDTO roleMenuDtlDTO = result.getData();
        List<String> perms = roleMenuDtlDTO.getPerms();
        if (CollUtil.isEmpty(perms)) {
            return false;
        }
        return Arrays.stream(permissions).anyMatch(perms::contains);
    }

}
