package com.ticho.upms.domain.repository;

import com.ticho.boot.datasource.service.RootService;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.interfaces.dto.UserAccountDTO;
import com.ticho.upms.interfaces.query.UserQuery;

import java.util.List;

/**
 * 用户信息 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface UserRepository extends RootService<User> {


    /**
     * 根据用户名查询用户
     *
     * @param tenantId 租户id
     * @param username 用户名
     * @return {@link User}
     */
    User getByUsername(String tenantId, String username);

    /**
     * 根据条件查询User列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link User}>
     */
    List<User> list(UserQuery query);

    /**
     * 根据用户登录账号信息查询
     *
     * @param userAccountDTO 用户登录账号信息
     * @return 用户信息
     */
    List<User> getByAccount(UserAccountDTO userAccountDTO);
}

