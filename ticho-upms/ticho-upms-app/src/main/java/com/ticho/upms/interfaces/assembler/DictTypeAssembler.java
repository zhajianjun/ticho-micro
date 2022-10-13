package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.DictType;
import com.ticho.upms.interfaces.dto.DictTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据字典类型 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface DictTypeAssembler {
    DictTypeAssembler INSTANCE = Mappers.getMapper(DictTypeAssembler.class);

    /**
     * 数据字典类型
     *
     * @param dto 数据字典类型DTO
     * @return {@link DictType}
     */
    DictType dtoToEntity(DictTypeDTO dto);

    /**
     * 数据字典类型DTO
     *
     * @param entity 数据字典类型
     * @return {@link DictTypeDTO}
     */
    DictTypeDTO entityToDto(DictType entity);

}