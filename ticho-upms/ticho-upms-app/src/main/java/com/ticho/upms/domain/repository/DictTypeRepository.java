package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.DictType;
import com.ticho.upms.interfaces.query.DictTypeQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 数据字典类型 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface DictTypeRepository extends IService<DictType> {

    /**
     * 保存数据字典类型
     *
     * @param dictType 数据字典类型 对象
     * @return boolean 是否保存成功
     */
    @Override
    boolean save(DictType dictType);

    /**
     * 批量保存数据字典类型
     *
     * @param dictType 数据字典类型 对象集合
     * @return boolean 是否保存成功
     */
    @Override
    boolean saveBatch(Collection<DictType> dictType);

    /**
     * 删除数据字典类型
     *
     * @param id 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 批量删除数据字典类型
     *
     * @param ids 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);

    /**
     * 修改数据字典类型
     *
     * @param dictType 数据字典类型 对象
     * @return boolean 是否修改成功
     */
    @Override
    boolean updateById(DictType dictType);

    /**
     * 根据id查询数据字典类型
     *
     * @param id 主键
     * @return {@link DictType}
     */
    @Override
    DictType getById(Serializable id);

    /**
     * 根据条件查询DictType列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link DictType}>
     */
    List<DictType> list(DictTypeQuery query);

}

