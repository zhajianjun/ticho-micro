package com.ticho.upms.infrastructure.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ticho.boot.datasource.service.impl.RootServiceImpl;
import com.ticho.upms.domain.repository.RoleFuncRepository;
import com.ticho.upms.infrastructure.entity.RoleFunc;
import com.ticho.upms.infrastructure.mapper.RoleFuncMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * 角色功能关联关系 repository实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Slf4j
@Service
public class RoleFuncRepositoryImpl extends RootServiceImpl<RoleFuncMapper, RoleFunc> implements RoleFuncRepository {

    @Override
    public boolean removeByMenuIdAndFuncIds(Long menuId, Collection<Long> funcIds) {
        if (Objects.isNull(menuId) || CollUtil.isEmpty(funcIds)) {
            return false;
        }
        LambdaQueryWrapper<RoleFunc> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(RoleFunc::getMenuId, menuId);
        wrapper.in(RoleFunc::getFuncId, funcIds);
        return remove(wrapper);
    }

}
