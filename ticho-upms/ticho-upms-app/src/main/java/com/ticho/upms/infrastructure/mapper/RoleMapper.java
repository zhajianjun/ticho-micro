package com.ticho.upms.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticho.upms.infrastructure.entity.Role;
import com.ticho.upms.interfaces.query.RoleQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色信息 mapper
 *
 * @author zhajianjun
 * @date 2022-10-10 17:28
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 批量保存 角色信息
     *
     * @param roles 角色信息 对象
     * @return int 数量
     */
    int insertBatch(List<Role> roles);

    /**
     * 有则更新无则插入
     *
     * <p>针对mysql、按照唯一索引来判断的</p>
     *
     * @param roles 角色信息
     * @return int 数量
     */
    int insertOrUpdate(Role roles);

    /**
     * 批量有则更新无则插入
     *
     * <p>注意：</p>
     * <p>1.仅同步任务且无频繁插入场景使用，高并发插入更新请不要用这种方式操作，会有死锁问题。</p>
     * <p>2.针对mysql、按照唯一索引来判断的</p>
     *
     * @param roles 角色信息 列表
     * @return int 数量
     */
    int insertOrUpdateBatch(List<Role> roles);

    /**
     * 根据条件查询 角色信息 列表
     *
     * @param query 条件
     * @return List<Role> 角色信息 列表
     */
    List<Role> selectByConditions(RoleQuery query);

}