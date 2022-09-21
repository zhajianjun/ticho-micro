package com.ticho.upms.security.service.impl;

import com.ticho.upms.security.SecurityConst;
import com.ticho.upms.security.entity.SecurityUser;
import com.ticho.upms.security.service.LoginVerifyStrategy;
import com.ticho.upms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 默认登录校验
 * @author zhajianjun
 * @date 2020-07-04 21:53
 */
@Service(SecurityConst.DEFAULT_TYPE)
@Slf4j
public class DefaultLoginVerifyStrategy implements LoginVerifyStrategy {

    @Autowired
    private UserService userService;

    @Override
    public SecurityUser loadUserByUsername(String username) {
        return userService.getByUsername(username);
    }
}
