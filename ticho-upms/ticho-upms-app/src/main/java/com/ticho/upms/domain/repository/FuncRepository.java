package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.interfaces.query.FuncQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 功能号信息 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface FuncRepository extends IService<Func> {

    /**
     * 保存功能号信息
     *
     * @param func 功能号信息 对象
     * @return boolean 是否保存成功
     */
    @Override
    boolean save(Func func);

    /**
     * 批量保存功能号信息
     *
     * @param func 功能号信息 对象集合
     * @return boolean 是否保存成功
     */
    @Override
    boolean saveBatch(Collection<Func> func);

    /**
     * 删除功能号信息
     *
     * @param id 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 批量删除功能号信息
     *
     * @param ids 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);

    /**
     * 修改功能号信息
     *
     * @param func 功能号信息 对象
     * @return boolean 是否修改成功
     */
    @Override
    boolean updateById(Func func);

    /**
     * 根据id查询功能号信息
     *
     * @param id 主键
     * @return {@link Func}
     */
    @Override
    Func getById(Serializable id);

    /**
     * 根据条件查询Func列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Func}>
     */
    List<Func> list(FuncQuery query);

}

