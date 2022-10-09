package com.ticho.upms.infrastructure.core.component;

import com.ticho.boot.security.handle.jwt.JwtExtra;
import com.ticho.common.security.dto.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-23 10:47
 */
@Component
public class DefaultJwtExtra implements JwtExtra {

    @Override
    public Map<String, Object> getExtra() {
        Map<String, Object> extMap = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof SecurityUser) {
            SecurityUser securityUser = (SecurityUser) principal;
            extMap.put("status", securityUser.getStatus());
        }
        return extMap;
    }

}
