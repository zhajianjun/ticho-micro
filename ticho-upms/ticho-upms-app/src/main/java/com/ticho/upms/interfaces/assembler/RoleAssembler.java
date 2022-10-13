package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.interfaces.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色信息 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface RoleAssembler {
    RoleAssembler INSTANCE = Mappers.getMapper(RoleAssembler.class);

    /**
     * 角色信息
     *
     * @param dto 角色信息DTO
     * @return {@link Role}
     */
    Role dtoToEntity(RoleDTO dto);

    /**
     * 角色信息DTO
     *
     * @param entity 角色信息
     * @return {@link RoleDTO}
     */
    RoleDTO entityToDto(Role entity);

}