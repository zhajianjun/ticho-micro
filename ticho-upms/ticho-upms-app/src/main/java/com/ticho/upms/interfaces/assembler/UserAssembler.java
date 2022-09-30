package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.interfaces.dto.UserDTO;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.interfaces.dto.UserUpdDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author zhajianjun
 * @date 2021-10-27 23:58
 */
@Mapper
public interface UserAssembler {
    UserAssembler INSTANCE = Mappers.getMapper(UserAssembler.class);


    /**
     * 用户dto
     *
     * @param user 用户
     * @return {@link UserDTO}
     */
    UserDTO userToDto(User user);


    /**
     * 用户信息
     *
     * @param userUpdDTO 用户更新对象
     * @return {@link User}
     */
    User updToUser(UserUpdDTO userUpdDTO);
}
