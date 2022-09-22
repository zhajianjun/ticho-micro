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
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 17:58
 */
@Component(SecurityConst.LOAD_USER_TYPE_USERNAME)
@ConditionalOnMissingBean(name = SecurityConst.LOGIN_USER_TYPE_USERNAME)
@Primary
public class DefaultUsernameLoginUserService implements LoginUserService {
    private final UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

    @Autowired
    private LoadUserContext loadUserContext;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SecurityUser login(String account, String credentials) {
        SecurityUser securityUser = loadUserContext.loadUser(account, SecurityConst.LOAD_USER_TYPE_USERNAME);
        userDetailsChecker.check(securityUser);
        Assert.isNotNull(securityUser, BizErrCode.PARAM_ERROR);
        String passwordAes = securityUser.getPassword();
        Assert.isTrue(passwordEncoder.matches(credentials, passwordAes), BizErrCode.FAIL, "密码输入不正确");
        return securityUser;
    }

}
