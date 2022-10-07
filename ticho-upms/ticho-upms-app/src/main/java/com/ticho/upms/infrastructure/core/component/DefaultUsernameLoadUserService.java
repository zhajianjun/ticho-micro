package com.ticho.upms.infrastructure.core.component;

import com.ticho.boot.security.constant.SecurityConst;
import com.ticho.boot.security.handle.load.LoadUserService;
import com.ticho.common.security.dto.SecurityUser;
import com.ticho.upms.interfaces.dto.UserDTO;
import com.ticho.upms.application.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

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
        UserDTO userUpdDTO = userService.getByUsername(account);
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
