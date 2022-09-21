package com.ticho.upms.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ticho.upms.security.entity.SecurityUser;
import com.ticho.upms.security.service.LoginHandleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Security登录服务
 * @author zhajianjun
 * @date 2020-07-02 20:10
 */
@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginHandleContext loginHandleContext;

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (StrUtil.isBlank(username)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        SecurityUser securityUser = loginHandleContext.loadUserByUsername(username);
        if (securityUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return securityUser;
    }
}
