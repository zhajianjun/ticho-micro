package com.ticho.uaa.security.service;

import com.ticho.uaa.security.SecurityConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
public class LoginVerifyContext {
    @Autowired
    private Map<String, LoginVerifyStrategy> strategyMap;

    @Autowired(required = false)
    private HttpServletRequest request;

    public void verify() {
        String type = request.getParameter("type");
        LoginVerifyStrategy loginVerifyStrategy = strategyMap.get(type);
        if (Objects.isNull(loginVerifyStrategy)) {
            loginVerifyStrategy = strategyMap.get(SecurityConst.DEFAULT_TYPE);
        }
        loginVerifyStrategy.verify(request);
    }
}
