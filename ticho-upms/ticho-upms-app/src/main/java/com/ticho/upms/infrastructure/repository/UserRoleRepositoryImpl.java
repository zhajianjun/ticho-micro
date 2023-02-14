package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.boot.json.util.JsonUtil;
import com.ticho.boot.redis.util.RedisUtil;
import com.ticho.upms.domain.repository.UserRoleRepository;
import com.ticho.upms.infrastructure.core.constant.RedisConst;
import com.ticho.upms.infrastructure.entity.UserRole;
import com.ticho.upms.infrastructure.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户角色关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class UserRoleRepositoryImpl extends RootServiceImpl<UserRoleMapper, UserRole> implements UserRoleRepository {

    @Autowired
    private RedisUtil<String, String> redisUtil;

    @PostConstruct
    public void init() {
        if (redisUtil.exists(RedisConst.USER_ROLE_LIST_KEY)) {
            return;
        }
        List<UserRole> list = list();
        saveCache(list);
    }

    @Override
    public boolean saveBatch(Collection<UserRole> entityList) {
        // @formatter:off
        boolean saveBatch = super.saveBatch(entityList);
        if (!saveBatch) {
            return false;
        }
        saveCache(entityList);
        return true;
        // @formatter:on
    }

    private void saveCache(Collection<UserRole> entityList) {
        Map<String, List<UserRole>> collect = entityList
            .stream()
            .collect(Collectors.groupingBy(x-> x.getUserId().toString(), Collectors.toList()));
        Map<String, String> result = collect
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, x -> JsonUtil.toJsonString(x.getValue())));
        redisUtil.hPutAll(RedisConst.USER_ROLE_LIST_KEY, result);
    }

    @Override
    public List<UserRole> listByUserId(Long userId) {
        if (Objects.isNull(userId)) {
            return Collections.emptyList();
        }
        String userRoleStr = redisUtil.hGet(RedisConst.USER_ROLE_LIST_KEY, userId.toString());
        if (StrUtil.isNotBlank(userRoleStr)) {
            return JsonUtil.toList(userRoleStr, UserRole.class);
        }
        LambdaQueryWrapper<UserRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserRole::getUserId, userId);
        List<UserRole> list = list(wrapper);
        redisUtil.hPut(RedisConst.USER_ROLE_LIST_KEY, userId.toString(), JsonUtil.toJsonString(list));
        return list;
    }

    @Override
    public boolean existsByRoleIds(Collection<Long> roleIds) {
        LambdaQueryWrapper<UserRole> wrapper = Wrappers.lambdaQuery();
        wrapper.in(UserRole::getRoleId, roleIds);
        wrapper.last("limit 1");
        return !list(wrapper).isEmpty();
    }

    @Override
    public boolean removeByUserId(Long userId) {
        if (Objects.isNull(userId)) {
            return false;
        }
        LambdaQueryWrapper<UserRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserRole::getUserId, userId);
        boolean remove = remove(wrapper);
        redisUtil.hDelete(RedisConst.USER_ROLE_LIST_KEY, userId.toString());
        return remove;
    }

}
