package com.ticho.common.security.service.load;

import com.ticho.common.security.constant.SecurityConst;
import com.ticho.common.security.dto.SecurityUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 11:17
 */
@Component(SecurityConst.LOAD_USER_TYPE_EMAIL)
@ConditionalOnMissingBean(name = SecurityConst.LOAD_USER_TYPE_EMAIL)
public class DefaultEmailUserDetailsService extends AbstractUserDetailsService {

    @Override
    public SecurityUser loadUser(String account) {
        return null;
    }

}
