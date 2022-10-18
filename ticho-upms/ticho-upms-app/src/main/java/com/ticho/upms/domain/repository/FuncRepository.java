package com.ticho.upms.domain.repository;

import com.ticho.boot.datasource.service.RootService;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.interfaces.query.FuncQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 功能号信息 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface FuncRepository extends RootService<Func> {

    /**
     * 保存或更新代码
     *
     * @param func 函数
     * @return boolean
     */
    boolean saveOrUpdateByCode(Func func);

    /**
     * 保存或更新批处理代码
     *
     * @param entityList 实体列表
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveOrUpdateBatchByCode(Collection<Func> entityList) {
        return saveOrUpdateBatchByCode(entityList, 200);
    }

    /**
     * 保存或更新批处理代码
     *
     * @param entityList 实体列表
     * @param batchSize 批量大小
     * @return boolean
     */
    boolean saveOrUpdateBatchByCode(Collection<Func> entityList, int batchSize);

    /**
     * 通过编码查询
     *
     * @param code 代码
     * @return {@link Func}
     */
    Func getByCode(String code);

    /**
     * 根据条件查询Func列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Func}>
     */
    List<Func> list(FuncQuery query);

}

