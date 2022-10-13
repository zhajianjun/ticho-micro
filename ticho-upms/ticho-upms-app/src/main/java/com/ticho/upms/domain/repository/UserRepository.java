package com.ticho.upms.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.interfaces.query.UserQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 用户信息 repository接口
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
public interface UserRepository extends IService<User> {

    /**
     * 保存用户信息
     *
     * @param user 用户信息 对象
     * @return boolean 是否保存成功
     */
    @Override
    boolean save(User user);

    /**
     * 批量保存用户信息
     *
     * @param user 用户信息 对象集合
     * @return boolean 是否保存成功
     */
    @Override
    boolean saveBatch(Collection<User> user);

    /**
     * 删除用户信息
     *
     * @param id 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 批量删除用户信息
     *
     * @param ids 主键
     * @return boolean 是否删除成功
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);

    /**
     * 修改用户信息
     *
     * @param user 用户信息 对象
     * @return boolean 是否修改成功
     */
    @Override
    boolean updateById(User user);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return {@link User}
     */
    User getByUsername(String username);

    /**
     * 根据id查询用户信息
     *
     * @param id 主键
     * @return {@link User}
     */
    @Override
    User getById(Serializable id);

    /**
     * 根据条件查询User列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link User}>
     */
    List<User> list(UserQuery query);

}

