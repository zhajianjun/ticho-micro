package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.interfaces.dto.FuncDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

/**
 * 功能号信息 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface FuncAssembler {
    FuncAssembler INSTANCE = Mappers.getMapper(FuncAssembler.class);

    /**
     * 功能号信息
     *
     * @param dto 功能号信息DTO
     * @return {@link Func}
     */
    Func dtoToEntity(FuncDTO dto);


    /**
     * 功能号信息列表
     *
     * @param dtos dtos
     * @return {@link List}<{@link Func}>
     */
    List<Func> dtoToEntitys(Collection<FuncDTO> dtos);

    /**
     * 功能号信息DTO
     *
     * @param entity 功能号信息
     * @return {@link FuncDTO}
     */
    FuncDTO entityToDto(Func entity);

}