package com.ticho.upms.application.service;

import com.ticho.boot.view.core.PageResult;
import com.ticho.upms.interfaces.dto.RoleDTO;
import com.ticho.upms.interfaces.query.RoleQuery;

import java.io.Serializable;

/**
 * 角色信息 服务接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface RoleService {
    /**
     * 保存角色信息
     *
     * @param roleDTO 角色信息DTO 对象
     */
    void save(RoleDTO roleDTO);

    /**
     * 删除角色信息
     *
     * @param id 主键
     */
    void removeById(Serializable id);

    /**
     * 修改角色信息
     *
     * @param roleDTO 角色信息DTO 对象
     */
    void updateById(RoleDTO roleDTO);

    /**
     * 根据id查询角色信息
     *
     * @param id 主键
     * @return {@link RoleDTO}
     */
    RoleDTO getById(Serializable id);

    /**
     * 分页查询角色信息列表
     *
     * @param query 查询
     * @return {@link PageResult}<{@link RoleDTO}>
     */
    PageResult<RoleDTO> page(RoleQuery query);

}

