package com.ticho.common.security.component;

import cn.hutool.core.convert.Convert;
import com.ticho.upms.interfaces.dto.SecurityUser;
import com.ticho.boot.security.constant.OAuth2Const;
import com.ticho.boot.security.constant.SecurityConst;
import com.ticho.boot.security.filter.AbstractAuthTokenFilter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * jwt token验证过滤器
 *
 * @author zhajianjun
 * @date 2022-09-24 14:14:06
 */
@Component(OAuth2Const.OAUTH2_TOKEN_FILTER_BEAN_NAME)
public class DefaultAuthenticationTokenFilter extends AbstractAuthTokenFilter<SecurityUser> {

    @Override
    public SecurityUser convert(Map<String, Object> decodeAndVerify) {
        // @formatter:off
        String username = Optional.ofNullable(decodeAndVerify.get(SecurityConst.USERNAME)).map(Object::toString).orElse(null);
        List<String> authorities = Optional.ofNullable(decodeAndVerify.get(SecurityConst.AUTHORITIES)).map(x-> Convert.toList(String.class, x)).orElse(null);
        Integer status = Optional.ofNullable(decodeAndVerify.get("status")).map(Convert::toInt).orElse(null);
        SecurityUser user = new SecurityUser();
        user.setUsername(username);
        user.setRoleIds(authorities);
        user.setStatus(status);
        return user;
        // @formatter:on
    }

}
