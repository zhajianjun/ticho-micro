package com.ticho.common.security.service.login;

import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.util.Assert;
import com.ticho.common.security.constant.SecurityConst;
import com.ticho.common.security.dto.SecurityUser;
import com.ticho.common.security.service.LoadUserContext;
import com.ticho.common.security.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 用户密码登录
 *
 * @author zhajianjun
 * @date 2022-09-22 17:58
 */
@Component(SecurityConst.LOGIN_USER_TYPE_USERNAME)
@ConditionalOnMissingBean(name = SecurityConst.LOGIN_USER_TYPE_USERNAME)
@Primary
public class DefaultUsernameLoginUserService implements LoginUserService {
    @Autowired
    private LoadUserContext loadUserContext;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsChecker userDetailsChecker;

    @Override
    public SecurityUser login(String account, String credentials) {
        // 查询用户信息
        SecurityUser securityUser = loadUserContext.loadUser(account, SecurityConst.LOAD_USER_TYPE_USERNAME);
        Assert.isNotNull(securityUser, BizErrCode.PARAM_ERROR);
        // 检查用户状态
        userDetailsChecker.check(securityUser);
        // 校验用户密码
        String passwordAes = securityUser.getPassword();
        Assert.isTrue(passwordEncoder.matches(credentials, passwordAes), BizErrCode.FAIL, "密码输入不正确");
        return securityUser;
    }

}
