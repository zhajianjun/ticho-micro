package com.ticho.upms.domain.repository;

import com.ticho.boot.datasource.service.RootService;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.interfaces.query.MenuQuery;

import java.util.List;

/**
 * 菜单信息 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface MenuRepository extends RootService<Menu> {

    /**
     * 根据条件查询Menu列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> list(MenuQuery query);

}

