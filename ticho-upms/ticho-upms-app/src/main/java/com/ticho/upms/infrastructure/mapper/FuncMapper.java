package com.ticho.upms.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticho.upms.infrastructure.entity.Func;
import com.ticho.upms.interfaces.query.FuncQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 功能号信息 mapper
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Repository
public interface FuncMapper extends BaseMapper<Func> {

    /**
     * 批量保存 功能号信息
     *
     * @param funcs 功能号信息 对象
     * @return int 数量
     */
    int insertBatch(@Param("ew") Collection<Func> funcs);

    /**
     * 有则更新无则插入
     *
     * <p>针对mysql、按照唯一索引来判断的</p>
     *
     * @param funcs 功能号信息
     * @return int 数量
     */
    int insertOrUpdate(Func funcs);

    /**
     * 批量有则更新无则插入
     *
     * <p>注意：</p>
     * <p>1.仅同步任务且无频繁插入场景使用，高并发插入更新请不要用这种方式操作，会有死锁问题。</p>
     * <p>2.针对mysql、按照唯一索引来判断的</p>
     *
     * @param funcs 功能号信息 列表
     * @return int 数量
     */
    int insertOrUpdateBatch(@Param("ew") Collection<Func> funcs);

    /**
     * 根据条件查询 功能号信息 列表
     *
     * @param query 查询条件
     * @return {@link List}<{@link Func}>
     */
    List<Func> selectByConditions(FuncQuery query);

}