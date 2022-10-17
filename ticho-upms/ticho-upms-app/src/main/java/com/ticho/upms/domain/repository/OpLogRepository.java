package com.ticho.upms.domain.repository;

import com.ticho.boot.datasource.service.RootService;
import com.ticho.upms.infrastructure.entity.OpLog;
import com.ticho.upms.interfaces.query.OpLogQuery;

import java.util.List;

/**
 * 日志信息 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface OpLogRepository extends RootService<OpLog> {

    /**
     * 根据条件查询OpLog列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link OpLog}>
     */
    List<OpLog> list(OpLogQuery query);

}

