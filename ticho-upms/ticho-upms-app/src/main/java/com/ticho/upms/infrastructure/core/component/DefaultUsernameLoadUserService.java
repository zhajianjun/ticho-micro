package com.ticho.upms.infrastructure.core.component;

import com.ticho.boot.security.constant.BaseSecurityConst;
import com.ticho.boot.security.handle.load.LoadUserService;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.HttpErrCode;
import com.ticho.boot.view.util.Assert;
import com.ticho.common.security.dto.SecurityUser;
import com.ticho.upms.domain.repository.RoleRepository;
import com.ticho.upms.domain.repository.TenantRepository;
import com.ticho.upms.domain.repository.UserRepository;
import com.ticho.upms.domain.repository.UserRoleRepository;
import com.ticho.upms.infrastructure.core.enums.TenantStatus;
import com.ticho.upms.infrastructure.core.enums.UserStatus;
import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.infrastructure.entity.Tenant;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.infrastructure.entity.UserRole;
import com.ticho.upms.interfaces.assembler.UserAssembler;
import com.ticho.upms.interfaces.dto.UpmsUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 11:17
 */
@Component(BaseSecurityConst.LOAD_USER_TYPE_USERNAME)
@Primary
@Slf4j
public class DefaultUsernameLoadUserService implements LoadUserService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired(required = false)
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public SecurityUser load(String account) {
        // @formatter:off
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        String tenantId = request.getParameter("tenantId");
        Assert.isNotBlank(tenantId, BizErrCode.FAIL, "租户id不能为空");
        // 租户信息校验
        Tenant tenant = tenantRepository.getByTenantId(tenantId);
        Assert.isNotNull(tenant, HttpErrCode.NOT_LOGIN, "租户不存在");
        Integer status = tenant.getStatus();
        String message = TenantStatus.getByCode(status);
        boolean normal = Objects.equals(status, TenantStatus.NORMAL.code());
        Assert.isTrue(normal, HttpErrCode.NOT_LOGIN, String.format("租户%s", message));
        // 用户信息校验
        User user = userRepository.getByUsername(tenantId, account);
        Assert.isNotNull(user, HttpErrCode.NOT_LOGIN, "用户或者密码不正确");
        status = user.getStatus();
        message = UserStatus.getByCode(status);
        normal = Objects.equals(status, UserStatus.NORMAL.code());
        Assert.isTrue(normal, HttpErrCode.NOT_LOGIN, String.format("用户%s", message));
        UpmsUserDTO upmsUserDTO = UserAssembler.INSTANCE.userToUmpsUsrDto(user);
        setAuthorities(upmsUserDTO);
        SecurityUser securityUser = new SecurityUser();
        securityUser.setTenantId(upmsUserDTO.getTenantId());
        securityUser.setUsername(upmsUserDTO.getUsername());
        securityUser.setPassword(upmsUserDTO.getPassword());
        securityUser.setRoleCodes(upmsUserDTO.getRoleIds());
        securityUser.setStatus(upmsUserDTO.getStatus());
        return securityUser;
        // @formatter:on
    }

    private void setAuthorities(UpmsUserDTO userUpdDto) {
        if (userUpdDto == null) {
            return;
        }
        List<UserRole> userRoles = userRoleRepository.listByUserId(userUpdDto.getId());
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Role> roles = roleRepository.listByIds(roleIds);
        List<String> codes = roles.stream().map(Role::getCode).collect(Collectors.toList());
        userUpdDto.setRoleIds(codes);
    }

}
