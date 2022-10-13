package com.ticho.upms.infrastructure.core.component;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.ticho.boot.security.constant.SecurityConst;
import com.ticho.boot.security.handle.load.LoadUserService;
import com.ticho.common.security.dto.SecurityUser;
import com.ticho.upms.application.service.UserService;
import com.ticho.upms.interfaces.dto.UpmsUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 11:17
 */
@Component(SecurityConst.LOAD_USER_TYPE_USERNAME)
@Primary
@Slf4j
public class DefaultUsernameLoadUserService implements LoadUserService {

    @Autowired(required = false)
    private UserService userService;

    @Override
    public SecurityUser load(String account) {
        // @formatter:off
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(header)) {
            return null;
        }
        header = header.replaceFirst("Basic", "").trim();
        String tenantId = Base64.decodeStr(header).split(":")[0];
        UpmsUserDTO userUpdDTO = userService.getByUsername(tenantId, account);
        if (userUpdDTO == null) {
            return null;
        }
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(userUpdDTO.getUsername());
        securityUser.setPassword(userUpdDTO.getPassword());
        securityUser.setRoleIds(userUpdDTO.getRoleIds());
        securityUser.setStatus(userUpdDTO.getStatus());
        return securityUser;
        // @formatter:on
    }

}
