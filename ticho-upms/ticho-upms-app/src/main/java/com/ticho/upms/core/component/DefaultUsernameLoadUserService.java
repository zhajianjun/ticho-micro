package com.ticho.upms.core.component;

import com.ticho.boot.security.constant.SecurityConst;
import com.ticho.boot.security.handle.load.LoadUserService;
import com.ticho.upms.dto.SecurityUser;
import com.ticho.upms.dto.UserDTO;
import com.ticho.upms.application.UserService;
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
        UserDTO userDTO = userService.getByUsername(account);
        if (userDTO == null) {
            return null;
        }
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(userDTO.getUsername());
        securityUser.setPassword(userDTO.getPassword());
        securityUser.setRoleIds(userDTO.getRoleIds());
        securityUser.setStatus(userDTO.getStatus());
        return securityUser;
        // @formatter:on
    }

}
