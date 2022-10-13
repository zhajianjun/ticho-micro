package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.MenuFunc;
import com.ticho.upms.interfaces.dto.MenuFuncDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 菜单功能关联关系 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface MenuFuncAssembler {
    MenuFuncAssembler INSTANCE = Mappers.getMapper(MenuFuncAssembler.class);

    /**
     * 菜单功能关联关系
     *
     * @param dto 菜单功能关联关系DTO
     * @return {@link MenuFunc}
     */
    MenuFunc dtoToEntity(MenuFuncDTO dto);

    /**
     * 菜单功能关联关系DTO
     *
     * @param entity 菜单功能关联关系
     * @return {@link MenuFuncDTO}
     */
    MenuFuncDTO entityToDto(MenuFunc entity);

}