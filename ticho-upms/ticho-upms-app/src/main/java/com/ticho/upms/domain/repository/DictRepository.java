package com.ticho.upms.domain.repository;

import com.ticho.boot.datasource.service.RootService;
import com.ticho.upms.infrastructure.entity.Dict;
import com.ticho.upms.interfaces.query.DictQuery;

import java.util.List;

/**
 * 数据字典 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface DictRepository extends RootService<Dict> {

    /**
     * 根据条件查询Dict列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Dict}>
     */
    List<Dict> list(DictQuery query);

    /**
     * 根据编号查询所有子孙节点的id
     *
     * @param id 主键id
     *
     * @return {@link List}<{@link Integer}>
     */
    List<Long> getDescendantIds(Long id);


    /**
     * 根据id查询兄弟节点，包括自己
     *
     * @param id  主键id
     * @return {@link List}<{@link Dict}>
     */
    List<Dict> getBrothers(Long id);
}

