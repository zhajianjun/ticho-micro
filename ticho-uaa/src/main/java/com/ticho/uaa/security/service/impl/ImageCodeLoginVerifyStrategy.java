package com.ticho.uaa.security.service.impl;

import com.ticho.uaa.security.SecurityConst;
import com.ticho.uaa.security.service.LoginVerifyStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码登录校验
 *
 * @author zhajianjun
 * @date 2020-07-04 22:12
 */
@Slf4j
@Component(SecurityConst.IMAGE_CODE_TYPE)
public class ImageCodeLoginVerifyStrategy implements LoginVerifyStrategy {

    @Override
    public void verify(HttpServletRequest httpServletRequest) {

    }
}
