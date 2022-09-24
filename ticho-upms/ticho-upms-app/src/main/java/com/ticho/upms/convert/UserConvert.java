package com.ticho.upms.convert;

import com.ticho.upms.dto.UserDTO;
import com.ticho.upms.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author zhajianjun
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
    UserDTO userToDto(User user);
}