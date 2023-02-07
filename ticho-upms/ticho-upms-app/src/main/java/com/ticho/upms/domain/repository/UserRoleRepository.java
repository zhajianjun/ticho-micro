package com.ticho.upms.domain.repository;

import com.ticho.boot.datasource.service.RootService;
import com.ticho.upms.infrastructure.entity.UserRole;

import java.util.Collection;
import java.util.List;

/**
 * 用户角色关联关系 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface UserRoleRepository extends RootService<UserRole> {

    /**
     * 通过用户id查询
     *
     * @param userId 菜单id
     * @return {@link List}<{@link UserRole}>
     */
    List<UserRole> listByUserId(Long userId);

    /**
     * 根据角色id列表查询是否存在
     *
     * @param roleIds 角色id
     * @return boolean
     */
    boolean existsByRoleIds(Collection<Long> roleIds);

    /**
     * 通过用户id删除
     *
     * @param userId 用户id
     * @return boolean
     */
    boolean removeByUserId(Long userId);

}

