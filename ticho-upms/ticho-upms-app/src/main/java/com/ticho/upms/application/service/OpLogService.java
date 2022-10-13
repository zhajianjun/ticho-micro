package com.ticho.upms.application.service;

import com.ticho.boot.view.core.PageResult;
import com.ticho.upms.interfaces.dto.OpLogDTO;
import com.ticho.upms.interfaces.query.OpLogQuery;

import java.io.Serializable;

/**
 * 日志信息 服务接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface OpLogService {
    /**
     * 保存日志信息
     *
     * @param opLogDTO 日志信息DTO 对象
     */
    void save(OpLogDTO opLogDTO);

    /**
     * 删除日志信息
     *
     * @param id 主键
     */
    void removeById(Serializable id);

    /**
     * 修改日志信息
     *
     * @param opLogDTO 日志信息DTO 对象
     */
    void updateById(OpLogDTO opLogDTO);

    /**
     * 根据id查询日志信息
     *
     * @param id 主键
     * @return {@link OpLogDTO}
     */
    OpLogDTO getById(Serializable id);

    /**
     * 分页查询日志信息列表
     *
     * @param query 查询
     * @return {@link PageResult}<{@link OpLogDTO}>
     */
    PageResult<OpLogDTO> page(OpLogQuery query);

}

