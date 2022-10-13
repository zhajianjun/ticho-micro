package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.RoleFunc;
import com.ticho.upms.interfaces.dto.RoleFuncDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色功能关联关系 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface RoleFuncAssembler {
    RoleFuncAssembler INSTANCE = Mappers.getMapper(RoleFuncAssembler.class);

    /**
     * 角色功能关联关系
     *
     * @param dto 角色功能关联关系DTO
     * @return {@link RoleFunc}
     */
    RoleFunc dtoToEntity(RoleFuncDTO dto);

    /**
     * 角色功能关联关系DTO
     *
     * @param entity 角色功能关联关系
     * @return {@link RoleFuncDTO}
     */
    RoleFuncDTO entityToDto(RoleFunc entity);

}