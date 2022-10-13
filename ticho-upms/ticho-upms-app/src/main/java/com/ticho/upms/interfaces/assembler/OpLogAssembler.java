package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.OpLog;
import com.ticho.upms.interfaces.dto.OpLogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 日志信息 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface OpLogAssembler {
    OpLogAssembler INSTANCE = Mappers.getMapper(OpLogAssembler.class);

    /**
     * 日志信息
     *
     * @param dto 日志信息DTO
     * @return {@link OpLog}
     */
    OpLog dtoToEntity(OpLogDTO dto);

    /**
     * 日志信息DTO
     *
     * @param entity 日志信息
     * @return {@link OpLogDTO}
     */
    OpLogDTO entityToDto(OpLog entity);

}