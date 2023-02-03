package com.ticho.upms.application.service;

import com.ticho.boot.view.core.PageResult;
import com.ticho.upms.interfaces.dto.RoleDTO;
import com.ticho.upms.interfaces.dto.RoleMenuDTO;
import com.ticho.upms.interfaces.dto.RoleMenuDtlDTO;
import com.ticho.upms.interfaces.query.RoleQuery;

import java.io.Serializable;
import java.util.List;

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
    void removeById(Long id);

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

    /**
     * 根据角色id列表合并菜单信息
     *
     * @param roleIds 角色id
     * @return {@link RoleMenuDtlDTO}
     */
    RoleMenuDtlDTO mergeMenuByRoleIds(List<Long> roleIds, boolean showAll);

    /**
     * 绑定菜单
     *
     * @param roleMenuDTO 用户角色dto
     */
    void bindMenu(RoleMenuDTO roleMenuDTO);

    /**
     * 用户角色菜单功能号详情
     *
     * @param roleId 角色id
     * @param showAll 是否展示全部
     * @return {@link RoleMenuDtlDTO}
     */
    RoleMenuDtlDTO getRoleDtl(Long roleId, boolean showAll);
}

