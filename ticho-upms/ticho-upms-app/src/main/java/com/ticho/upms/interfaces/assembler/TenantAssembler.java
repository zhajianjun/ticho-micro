package com.ticho.upms.interfaces.assembler;

import com.ticho.upms.infrastructure.entity.Tenant;
import com.ticho.upms.interfaces.dto.TenantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 租户信息 转换
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Mapper
public interface TenantAssembler {
    TenantAssembler INSTANCE = Mappers.getMapper(TenantAssembler.class);

    /**
     * 租户信息
     *
     * @param dto 租户信息DTO
     * @return {@link Tenant}
     */
    Tenant dtoToEntity(TenantDTO dto);

    /**
     * 租户信息DTO
     *
     * @param entity 租户信息
     * @return {@link TenantDTO}
     */
    TenantDTO entityToDto(Tenant entity);

}