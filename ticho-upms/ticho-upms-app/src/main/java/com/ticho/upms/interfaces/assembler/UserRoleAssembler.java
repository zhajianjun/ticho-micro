package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.UserRole;
import com.ticho.upms.interfaces.dto.UserRoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户角色关联关系 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface UserRoleAssembler {
    UserRoleAssembler INSTANCE = Mappers.getMapper(UserRoleAssembler.class);

    /**
     * 用户角色关联关系
     *
     * @param dto 用户角色关联关系DTO
     * @return {@link UserRole}
     */
    UserRole dtoToEntity(UserRoleDTO dto);

    /**
     * 用户角色关联关系DTO
     *
     * @param entity 用户角色关联关系
     * @return {@link UserRoleDTO}
     */
    UserRoleDTO entityToDto(UserRole entity);

}