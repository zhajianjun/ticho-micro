package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.interfaces.query.FuncQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 功能号信息 mapper
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Repository
public interface FuncMapper extends RootMapper<Func> {

    /**
     * 根据条件查询 功能号信息 列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Func}>
     */
    List<Func> selectByConditions(FuncQuery query);

}