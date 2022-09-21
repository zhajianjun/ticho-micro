package com.ticho.upms.security.service.impl;

import com.ticho.upms.security.SecurityConst;
import com.ticho.upms.security.entity.SecurityUser;
import com.ticho.upms.security.service.LoginVerifyStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 验证码登录校验
 *
 * @author zhajianjun
 * @date 2020-07-04 22:12
 */
@Slf4j
@Component(SecurityConst.EMAIL_TYPE)
public class EmailLoginVerifyStrategy implements LoginVerifyStrategy {


    @Override
    public SecurityUser loadUserByUsername(String username) {
        return null;
    }

}
