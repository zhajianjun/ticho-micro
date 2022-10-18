package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.interfaces.query.UserQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户信息 mapper
 *
 * @author zhajianjun
 * @date 2022-10-18 09:08
 */
@Repository
public interface UserMapper extends RootMapper<User> {

}