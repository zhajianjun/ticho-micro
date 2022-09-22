package com.ticho.common.security.service.load;

import com.ticho.boot.view.core.Result;
import com.ticho.common.security.constant.SecurityConst;
import com.ticho.common.security.dto.SecurityUser;
import com.ticho.upms.api.UserBizFeignService;
import com.ticho.upms.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 11:17
 */
@Component(SecurityConst.LOAD_USER_TYPE_USERNAME)
@ConditionalOnMissingBean(name = SecurityConst.LOAD_USER_TYPE_USERNAME)
@Primary
public class DefaultUsernameUserDetailsService extends AbstractUserDetailsService {

    @Autowired(required = false)
    private UserBizFeignService userBizFeignService;

    @Override
    public SecurityUser loadUser(String account) {
        Result<UserDTO> result = userBizFeignService.getByUsername(account);
        UserDTO data = result.getData();
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(data.getUsername());
        securityUser.setPassword(data.getPassword());
        securityUser.setStatus(data.getStatus());
        List<String> roleIds = data.getRoleIds();
        List<SimpleGrantedAuthority> authorities = roleIds
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        securityUser.setAuthorities(authorities);
        return securityUser;
    }

}
