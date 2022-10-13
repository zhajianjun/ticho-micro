package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.RoleRes;
import com.ticho.upms.interfaces.dto.RoleResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色和资源关联表 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface RoleResAssembler {
    RoleResAssembler INSTANCE = Mappers.getMapper(RoleResAssembler.class);

    /**
     * 角色和资源关联表
     *
     * @param dto 角色和资源关联表DTO
     * @return {@link RoleRes}
     */
    RoleRes dtoToEntity(RoleResDTO dto);

    /**
     * 角色和资源关联表DTO
     *
     * @param entity 角色和资源关联表
     * @return {@link RoleResDTO}
     */
    RoleResDTO entityToDto(RoleRes entity);

}