package com.ticho.upms.infrastructure.mapper;

import com.ticho.boot.datasource.mapper.RootMapper;
import com.ticho.upms.infrastructure.entity.Dict;
import org.springframework.stereotype.Repository;

/**
 * 数据字典 mapper
 *
 * @author zhajianjun
 * @date 2022-10-18 09:08
 */
@Repository
public interface DictMapper extends RootMapper<Dict> {

}