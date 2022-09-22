package com.ticho.common.security.service;

import com.ticho.common.security.constant.SecurityConst;
import com.ticho.common.security.dto.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 18:12
 */
@Component
public class LoginUserContext {

    @Autowired
    private Map<String, LoginUserService> loginUserServiceMap;

    public SecurityUser loadUser(String account, String credentials, String type) {
        LoginUserService loadUserService = loginUserServiceMap.get(type);
        if (loadUserService == null) {
            loadUserService = loginUserServiceMap.get(SecurityConst.LOGIN_USER_TYPE_USERNAME);
        }
        return loadUserService.login(account, credentials);
    }

}
