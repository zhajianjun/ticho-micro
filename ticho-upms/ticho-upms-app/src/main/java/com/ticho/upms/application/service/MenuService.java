package com.ticho.upms.application.service;

import com.ticho.upms.interfaces.dto.MenuDTO;
import com.ticho.upms.interfaces.dto.MenuDtlDTO;

import java.util.List;

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
    void removeById(Long id);

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
    MenuDTO getById(Long id);

    /**
     * 获取所有菜单信息
     *
     * @return {@link List}<{@link MenuDtlDTO}>
     */
    List<MenuDtlDTO> list();

}

