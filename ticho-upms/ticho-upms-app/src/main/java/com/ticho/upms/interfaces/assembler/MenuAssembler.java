package com.ticho.upms.interfaces.assembler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.interfaces.dto.MenuDTO;
import com.ticho.upms.interfaces.dto.MenuDtlDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 菜单信息 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper(imports = {StrUtil.class, CollUtil.class})
public interface MenuAssembler {
    MenuAssembler INSTANCE = Mappers.getMapper(MenuAssembler.class);

    /**
     * 菜单信息
     *
     * @param dto 菜单信息DTO
     * @return {@link Menu}
     */
    @Mapping(target = "perms", expression = "java(CollUtil.join(dto.getPerms(), \",\"))")
    Menu dtoToEntity(MenuDTO dto);

    /**
     * 菜单信息DTO
     *
     * @param entity 菜单信息
     * @return {@link MenuDTO}
     */
    @Mapping(target = "perms", expression = "java(StrUtil.split(entity.getPerms(), ','))")
    MenuDTO entityToDto(Menu entity);

    @Mapping(target = "perms", expression = "java(StrUtil.split(entity.getPerms(), ','))")
    MenuDtlDTO entityToDtlDto(Menu entity);

}