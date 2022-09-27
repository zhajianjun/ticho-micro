package com.ticho.auth.component;

import com.ticho.auth.dto.SecurityUser;
import com.ticho.boot.security.constant.SecurityConst;
import com.ticho.boot.security.handle.load.LoadUserService;
import com.ticho.boot.view.core.Result;
import com.ticho.upms.api.UserBizFeignService;
import com.ticho.upms.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 11:17
 */
@Component(SecurityConst.LOAD_USER_TYPE_USERNAME)
@Primary
@Slf4j
public class DefaultUsernameLoadUserService implements LoadUserService {

    @Autowired(required = false)
    private UserBizFeignService userBizFeignService;

    @Override
    public SecurityUser load(String account) {
        // @formatter:off
        Result<UserDTO> result = userBizFeignService.getByUsername(account);
        UserDTO data = result.getData();
        if (data == null) {
            return null;
        }
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(data.getUsername());
        securityUser.setPassword(data.getPassword());
        securityUser.setRoleIds(data.getRoleIds());
        securityUser.setStatus(data.getStatus());
        return securityUser;
        // @formatter:on
    }

}
