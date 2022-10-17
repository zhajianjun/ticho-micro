package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.DictType;
import com.ticho.upms.interfaces.query.DictTypeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据字典类型 mapper
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Repository
public interface DictTypeMapper extends RootMapper<DictType> {

    /**
     * 根据条件查询 数据字典类型 列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link DictType}>
     */
    List<DictType> selectByConditions(DictTypeQuery query);

}