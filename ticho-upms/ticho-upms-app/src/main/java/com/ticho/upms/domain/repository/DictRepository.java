package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.Dict;
import com.ticho.upms.interfaces.query.DictQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 数据字典 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface DictRepository extends IService<Dict> {

    /**
     * 保存数据字典
     *
     * @param dict 数据字典 对象
     * @return boolean 是否保存成功
     */
    @Override
    boolean save(Dict dict);

    /**
     * 批量保存数据字典
     *
     * @param dict 数据字典 对象集合
     * @return boolean 是否保存成功
     */
    @Override
    boolean saveBatch(Collection<Dict> dict);

    /**
     * 删除数据字典
     *
     * @param id 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 批量删除数据字典
     *
     * @param ids 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);

    /**
     * 修改数据字典
     *
     * @param dict 数据字典 对象
     * @return boolean 是否修改成功
     */
    @Override
    boolean updateById(Dict dict);

    /**
     * 根据id查询数据字典
     *
     * @param id 主键
     * @return {@link Dict}
     */
    @Override
    Dict getById(Serializable id);

    /**
     * 根据条件查询Dict列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Dict}>
     */
    List<Dict> list(DictQuery query);

}

