package com.ticho.common.security.component;

import com.ticho.boot.security.constant.OAuth2Const;
import com.ticho.boot.security.filter.AbstractAuthTokenFilter;
import com.ticho.boot.web.util.JsonUtil;
import com.ticho.common.security.dto.SecurityUser;
import org.springframework.stereotype.Component;

import java.util.Map;

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
        return JsonUtil.convert(decodeAndVerify, SecurityUser.class);
    }

}
