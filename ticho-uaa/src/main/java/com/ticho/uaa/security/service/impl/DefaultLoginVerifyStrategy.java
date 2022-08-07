package com.ticho.uaa.security.service.impl;

import com.ticho.uaa.security.SecurityConst;
import com.ticho.uaa.security.service.LoginVerifyStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认登录校验
 * @author zhajianjun
 * @date 2020-07-04 21:53
 */
@Service(SecurityConst.DEFAULT_TYPE)
@Slf4j
public class DefaultLoginVerifyStrategy implements LoginVerifyStrategy {

    /**
     * 登录校验
     * @param httpServletRequest 客户端的请求
     */
    @Override
    public void verify(HttpServletRequest httpServletRequest) {
        log.debug("默认登录校验");
    }
}
