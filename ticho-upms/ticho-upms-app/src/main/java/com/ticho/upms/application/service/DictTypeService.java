package com.ticho.upms.application.service;

import com.ticho.boot.view.core.PageResult;
import com.ticho.upms.interfaces.dto.DictTypeDTO;
import com.ticho.upms.interfaces.query.DictTypeQuery;

import java.io.Serializable;

/**
 * 数据字典类型 服务接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface DictTypeService {
    /**
     * 保存数据字典类型
     *
     * @param dictTypeDTO 数据字典类型DTO 对象
     */
    void save(DictTypeDTO dictTypeDTO);

    /**
     * 删除数据字典类型
     *
     * @param id 主键
     */
    void removeById(Serializable id);

    /**
     * 修改数据字典类型
     *
     * @param dictTypeDTO 数据字典类型DTO 对象
     */
    void updateById(DictTypeDTO dictTypeDTO);

    /**
     * 根据id查询数据字典类型
     *
     * @param id 主键
     * @return {@link DictTypeDTO}
     */
    DictTypeDTO getById(Serializable id);

    /**
     * 分页查询数据字典类型列表
     *
     * @param query 查询
     * @return {@link PageResult}<{@link DictTypeDTO}>
     */
    PageResult<DictTypeDTO> page(DictTypeQuery query);

}

