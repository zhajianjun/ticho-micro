package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.interfaces.query.MenuQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单信息 mapper
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Repository
public interface MenuMapper extends RootMapper<Menu> {

    /**
     * 根据条件查询 菜单信息 列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> selectByConditions(MenuQuery query);

}