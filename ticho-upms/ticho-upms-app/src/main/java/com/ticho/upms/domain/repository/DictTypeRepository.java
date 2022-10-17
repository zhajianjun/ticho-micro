package com.ticho.upms.domain.repository;

import com.ticho.boot.datasource.service.RootService;
import com.ticho.upms.infrastructure.entity.DictType;
import com.ticho.upms.interfaces.query.DictTypeQuery;

import java.util.List;

/**
 * 数据字典类型 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface DictTypeRepository extends RootService<DictType> {

    /**
     * 根据条件查询DictType列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link DictType}>
     */
    List<DictType> list(DictTypeQuery query);

}

