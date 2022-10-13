package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.RoleMenu;
import com.ticho.upms.interfaces.dto.RoleMenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色菜单关联关系 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface RoleMenuAssembler {
    RoleMenuAssembler INSTANCE = Mappers.getMapper(RoleMenuAssembler.class);

    /**
     * 角色菜单关联关系
     *
     * @param dto 角色菜单关联关系DTO
     * @return {@link RoleMenu}
     */
    RoleMenu dtoToEntity(RoleMenuDTO dto);

    /**
     * 角色菜单关联关系DTO
     *
     * @param entity 角色菜单关联关系
     * @return {@link RoleMenuDTO}
     */
    RoleMenuDTO entityToDto(RoleMenu entity);

}