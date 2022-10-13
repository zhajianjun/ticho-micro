package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.OpLog;
import com.ticho.upms.interfaces.query.OpLogQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 日志信息 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface OpLogRepository extends IService<OpLog> {

    /**
     * 保存日志信息
     *
     * @param opLog 日志信息 对象
     * @return boolean 是否保存成功
     */
    @Override
    boolean save(OpLog opLog);

    /**
     * 批量保存日志信息
     *
     * @param opLog 日志信息 对象集合
     * @return boolean 是否保存成功
     */
    @Override
    boolean saveBatch(Collection<OpLog> opLog);

    /**
     * 删除日志信息
     *
     * @param id 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 批量删除日志信息
     *
     * @param ids 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);

    /**
     * 修改日志信息
     *
     * @param opLog 日志信息 对象
     * @return boolean 是否修改成功
     */
    @Override
    boolean updateById(OpLog opLog);

    /**
     * 根据id查询日志信息
     *
     * @param id 主键
     * @return {@link OpLog}
     */
    @Override
    OpLog getById(Serializable id);

    /**
     * 根据条件查询OpLog列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link OpLog}>
     */
    List<OpLog> list(OpLogQuery query);

}

