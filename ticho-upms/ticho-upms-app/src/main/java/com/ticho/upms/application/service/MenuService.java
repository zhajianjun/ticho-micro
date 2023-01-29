package com.ticho.upms.application.service;

import com.ticho.boot.view.core.PageResult;
import com.ticho.upms.interfaces.dto.MenuDTO;
import com.ticho.upms.interfaces.dto.MenuFuncDTO;
import com.ticho.upms.interfaces.query.MenuQuery;

import java.io.Serializable;

/**
 * 菜单信息 服务接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface MenuService {
    /**
     * 保存菜单信息
     *
     * @param menuDTO 菜单信息DTO 对象
     */
    void save(MenuDTO menuDTO);

    /**
     * 删除菜单信息
     *
     * @param id 主键
     */
    void removeById(Serializable id);

    /**
     * 修改菜单信息
     *
     * @param menuDTO 菜单信息DTO 对象
     */
    void updateById(MenuDTO menuDTO);

    /**
     * 根据id查询菜单信息
     *
     * @param id 主键
     * @return {@link MenuDTO}
     */
    MenuDTO getById(Serializable id);

    /**
     * 分页查询菜单信息列表
     *
     * @param query 查询
     * @return {@link PageResult}<{@link MenuDTO}>
     */
    PageResult<MenuDTO> page(MenuQuery query);

    /**
     * 菜单添加功能号
     *
     * @param menuFuncDTO 菜单功能号信息
     */
    void saveFunc(MenuFuncDTO menuFuncDTO);

    /**
     * 菜单移除功能号
     *
     * @param menuFuncDTO 菜单功能号信息
     */
    void removeFunc(MenuFuncDTO menuFuncDTO);

}

