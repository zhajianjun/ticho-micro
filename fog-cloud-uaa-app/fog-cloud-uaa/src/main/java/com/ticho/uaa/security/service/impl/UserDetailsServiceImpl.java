package com.ticho.uaa.security.service.impl;

import com.ticho.uaa.security.entity.SecurityUser;
import com.ticho.uaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Security登录服务
 * @author AdoroTutto
 * @date 2020-07-02 20:10
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SecurityUser byUsername = userService.getByUsername(username);
        if (username == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return byUsername;
    }
}
