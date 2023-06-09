package com.ticho.upms.domain.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.ticho.boot.security.auth.PermissionService;
import com.ticho.common.security.constant.SecurityConst;
import com.ticho.common.security.dto.SecurityUser;
import com.ticho.common.security.util.UserUtil;
import com.ticho.upms.domain.handle.UpmsHandle;
import com.ticho.upms.infrastructure.core.constant.CommConst;
import com.ticho.upms.interfaces.dto.RoleMenuDtlDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

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
@Service(CommConst.PERM_KEY)
@Order(1)
public class DefaultPermissionServiceImpl extends UpmsHandle implements PermissionService {

    public boolean hasPerms(String... permissions) {
        log.debug("权限校验，permissions = {}", String.join(",", permissions));
        if (ArrayUtil.isEmpty(permissions)) {
            return false;
        }
        SecurityUser currentUser = UserUtil.getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return false;
        }
        List<String> roleCodes = currentUser.getRoles();
        String tenantId = currentUser.getTenantId();
        if (CollUtil.isEmpty(roleCodes)) {
            return false;
        }
        if (roleCodes.contains(SecurityConst.ADMIN)) {
            return true;
        }
        RoleMenuDtlDTO roleMenuDtlDTO = mergeRoleByCodes(tenantId, roleCodes, false);
        List<String> perms = roleMenuDtlDTO.getPerms();
        if (CollUtil.isEmpty(perms)) {
            return false;
        }
        return Arrays.stream(permissions).anyMatch(perms::contains);
    }

}
