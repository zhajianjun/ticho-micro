package com.ticho.uaa.convert;

import com.ticho.uaa.entity.User;
import com.ticho.uaa.security.entity.SecurityUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author AdoroTutto
 * @date 2021-10-27 23:58
 */
@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    /**
     * security用户转换
     *
     * @param user 用户信息
     * @return security用户
     */
    SecurityUser userToSecurityUser(User user);
}
