package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.Dict;
import com.ticho.upms.interfaces.query.DictQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据字典 mapper
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Repository
public interface DictMapper extends RootMapper<Dict> {

    /**
     * 根据条件查询 数据字典 列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Dict}>
     */
    List<Dict> selectByConditions(DictQuery query);

}