package com.ticho.common.security.service.load;

import com.ticho.common.security.dto.SecurityUser;
import com.ticho.common.security.service.LoadUserService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 用户查询服务
 *
 * @author zhajianjun
 * @date 2022-09-22 10:07
 */
public abstract class AbstractUserDetailsService implements UserDetailsService, LoadUserService {

    public SecurityUser loadUserByUsername(String username) {
        return loadUser(username);
    }
}
