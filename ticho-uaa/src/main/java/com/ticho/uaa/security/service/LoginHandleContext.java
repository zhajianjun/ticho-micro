package com.ticho.uaa.security.service;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.ticho.uaa.entity.OauthClient;
import com.ticho.uaa.entity.OauthCode;
import com.ticho.uaa.security.SecurityConst;
import com.ticho.uaa.security.entity.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Security;
import java.util.Map;
import java.util.Objects;

/**
 * 登录策略执行
 *
 * @author zhajianjun
 * @date 2020-07-04 22:14
 */

@Service
@Slf4j
public class LoginHandleContext {
    public static final ThreadLocal<OauthClient> oauthClientThreadLocal = new TransmittableThreadLocal<>();
    public static final ThreadLocal<OauthCode> oauthCodeThreadLocal = new TransmittableThreadLocal<>();
    public static final ThreadLocal<SecurityUser> userDetailsThreadLocal = new TransmittableThreadLocal<>();

    @Autowired
    private Map<String, LoginVerifyStrategy> strategyMap;

    @Autowired(required = false)
    private HttpServletRequest request;

    public SecurityUser loadUserByUsername(String username) {
        String type = request.getParameter("type");
        LoginVerifyStrategy loginVerifyStrategy = strategyMap.get(type);
        if (Objects.isNull(loginVerifyStrategy)) {
            loginVerifyStrategy = strategyMap.get(SecurityConst.DEFAULT_TYPE);
        }
        SecurityUser securityUser = userDetailsThreadLocal.get();
        if (securityUser == null) {
            securityUser = loginVerifyStrategy.loadUserByUsername(username);
            LoginHandleContext.userDetailsThreadLocal.set(securityUser);
        }
        return securityUser;
    }
}
