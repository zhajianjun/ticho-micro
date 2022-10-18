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
 * @date 2022-10-18 09:08
 */
@Repository
public interface DictTypeMapper extends RootMapper<DictType> {

}