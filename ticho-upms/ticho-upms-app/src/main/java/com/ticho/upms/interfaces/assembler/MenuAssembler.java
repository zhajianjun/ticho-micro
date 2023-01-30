package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.interfaces.dto.MenuDTO;
import com.ticho.upms.interfaces.dto.MenuFuncDtlDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 菜单信息 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface MenuAssembler {
    MenuAssembler INSTANCE = Mappers.getMapper(MenuAssembler.class);

    /**
     * 菜单信息
     *
     * @param dto 菜单信息DTO
     * @return {@link Menu}
     */
    Menu dtoToEntity(MenuDTO dto);

    /**
     * 菜单信息DTO
     *
     * @param entity 菜单信息
     * @return {@link MenuDTO}
     */
    MenuDTO entityToDto(Menu entity);

    MenuFuncDtlDTO entityToDtlDto(Menu entity);

}