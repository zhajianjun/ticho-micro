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
 * @date 2022-09-22 10:44
 */
@Component
public class LoadUserContext {

    @Autowired
    private Map<String, LoadUserService> detailsServiceMap;

    public SecurityUser loadUser(String account, String type) {
        LoadUserService loadUserService = detailsServiceMap.get(type);
        if (loadUserService == null) {
            loadUserService = detailsServiceMap.get(SecurityConst.LOAD_USER_TYPE_USERNAME);
        }
        return loadUserService.loadUser(account);
    }
}
