package com.ticho.upms.domain.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.upms.application.service.DictService;
import com.ticho.upms.domain.repository.DictRepository;
import com.ticho.upms.infrastructure.entity.Dict;
import com.ticho.upms.interfaces.assembler.DictAssembler;
import com.ticho.upms.interfaces.dto.DictDTO;
import com.ticho.upms.interfaces.query.DictQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据字典 服务实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictRepository dictRepository;

    @Override
    public void save(DictDTO dictDTO) {
        Dict dict = DictAssembler.INSTANCE.dtoToEntity(dictDTO);
        Assert.isTrue(dictRepository.save(dict), BizErrCode.FAIL, "保存失败");
    }

    @Override
    public void removeById(Serializable id) {
        Assert.isTrue(dictRepository.removeById(id), BizErrCode.FAIL, "删除失败");
    }

    @Override
    public void updateById(DictDTO dictDTO) {
        Dict dict = DictAssembler.INSTANCE.dtoToEntity(dictDTO);
        Assert.isTrue(dictRepository.updateById(dict), BizErrCode.FAIL, "修改失败");
    }

    @Override
    public DictDTO getById(Serializable id) {
        Dict dict = dictRepository.getById(id);
        return DictAssembler.INSTANCE.entityToDto(dict);
    }

    @Override
    public PageResult<DictDTO> page(DictQuery query) {
        // @formatter:off
        query.checkPage();
        Page<Dict> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        dictRepository.list(query);
        List<DictDTO> dictDTOs = page.getResult()
            .stream()
            .map(DictAssembler.INSTANCE::entityToDto)
            .collect(Collectors.toList());
        return new PageResult<>(page.getPageNum(), page.getPageSize(), page.getTotal(), dictDTOs);
        // @formatter:on
    }
}
