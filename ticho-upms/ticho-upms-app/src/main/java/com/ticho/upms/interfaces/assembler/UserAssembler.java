package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.interfaces.dto.UpmsUserDTO;
import com.ticho.upms.interfaces.dto.UserAccountDTO;
import com.ticho.upms.interfaces.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 用户信息 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface UserAssembler {
    UserAssembler INSTANCE = Mappers.getMapper(UserAssembler.class);

    /**
     * 用户信息
     *
     * @param dto 用户信息DTO
     * @return {@link User}
     */
    User dtoToEntity(UserDTO dto);

    /**
     * 用户信息DTO
     *
     * @param entity 用户信息
     * @return {@link UserDTO}
     */
    UserDTO entityToDto(User entity);

    /**
     * 权限用户信息DTO
     *
     * @param user 用户
     * @return {@link UpmsUserDTO}
     */
    @Mapping(target = "roleIds", ignore = true)
    @Mapping(target = "deptIds", ignore = true)
    UpmsUserDTO userToUmpsUsrDto(User user);

    /**
     * 用户转用户登录账号信息
     *
     * @param user 用户
     * @return {@link UserAccountDTO}
     */
    @Mapping(target = "status", ignore = true)
    UserAccountDTO entityToAccount(User user);
}