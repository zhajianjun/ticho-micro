package com.ticho.common.security.util;

import com.ticho.boot.security.util.BaseSecurityUtil;
import com.ticho.common.security.dto.SecurityUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-10-14 13:49
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {


    public static SecurityUser getCurrentUser() {
        return BaseSecurityUtil.getCurrentUser();
    }

    public static String getCurrentUsername() {
        return BaseSecurityUtil.getCurrentUsername();
    }

}
