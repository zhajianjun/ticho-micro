package com.ticho.upms.application.service;

import com.ticho.boot.view.core.PageResult;
import com.ticho.upms.interfaces.dto.FuncDTO;
import com.ticho.upms.interfaces.query.FuncQuery;

import java.io.Serializable;

/**
 * 功能号信息 服务接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface FuncService {
    /**
     * 保存功能号信息
     *
     * @param funcDTO 功能号信息DTO 对象
     */
    void save(FuncDTO funcDTO);

    /**
     * 删除功能号信息
     *
     * @param id 主键
     */
    void removeById(Serializable id);

    /**
     * 修改功能号信息
     *
     * @param funcDTO 功能号信息DTO 对象
     */
    void updateById(FuncDTO funcDTO);

    /**
     * 根据id查询功能号信息
     *
     * @param id 主键
     * @return {@link FuncDTO}
     */
    FuncDTO getById(Serializable id);

    /**
     * 分页查询功能号信息列表
     *
     * @param query 查询
     * @return {@link PageResult}<{@link FuncDTO}>
     */
    PageResult<FuncDTO> page(FuncQuery query);

}

