package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.Dict;
import com.ticho.upms.interfaces.dto.DictDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据字典 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface DictAssembler {
    DictAssembler INSTANCE = Mappers.getMapper(DictAssembler.class);

    /**
     * 数据字典
     *
     * @param dto 数据字典DTO
     * @return {@link Dict}
     */
    Dict dtoToEntity(DictDTO dto);

    /**
     * 数据字典DTO
     *
     * @param entity 数据字典
     * @return {@link DictDTO}
     */
    DictDTO entityToDto(Dict entity);

}