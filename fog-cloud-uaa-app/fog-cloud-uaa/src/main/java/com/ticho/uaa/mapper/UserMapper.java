package com.ticho.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticho.uaa.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户 mapper
 *
 * @author AdoroTutto
 * @date 2021-10-24 22:12
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据条件查询 用户 列表
     *
     * @param user 条件
     * @return List<User> 用户 列表
     */
    List<User> selectByConditions(User user);

}