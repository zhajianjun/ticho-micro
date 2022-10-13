package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.interfaces.query.MenuQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 菜单信息 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface MenuRepository extends IService<Menu> {

    /**
     * 保存菜单信息
     *
     * @param menu 菜单信息 对象
     * @return boolean 是否保存成功
     */
    @Override
    boolean save(Menu menu);

    /**
     * 批量保存菜单信息
     *
     * @param menu 菜单信息 对象集合
     * @return boolean 是否保存成功
     */
    @Override
    boolean saveBatch(Collection<Menu> menu);

    /**
     * 删除菜单信息
     *
     * @param id 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 批量删除菜单信息
     *
     * @param ids 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);

    /**
     * 修改菜单信息
     *
     * @param menu 菜单信息 对象
     * @return boolean 是否修改成功
     */
    @Override
    boolean updateById(Menu menu);

    /**
     * 根据id查询菜单信息
     *
     * @param id 主键
     * @return {@link Menu}
     */
    @Override
    Menu getById(Serializable id);

    /**
     * 根据条件查询Menu列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> list(MenuQuery query);

}

