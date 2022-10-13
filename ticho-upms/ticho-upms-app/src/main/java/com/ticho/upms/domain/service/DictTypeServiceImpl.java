package com.ticho.upms.domain.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.upms.application.service.DictTypeService;
import com.ticho.upms.domain.repository.DictTypeRepository;
import com.ticho.upms.infrastructure.entity.DictType;
import com.ticho.upms.interfaces.assembler.DictTypeAssembler;
import com.ticho.upms.interfaces.dto.DictTypeDTO;
import com.ticho.upms.interfaces.query.DictTypeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据字典类型 服务实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {

    @Autowired
    private DictTypeRepository dictTypeRepository;

    @Override
    public void save(DictTypeDTO dictTypeDTO) {
        DictType dictType = DictTypeAssembler.INSTANCE.dtoToEntity(dictTypeDTO);
        Assert.isTrue(dictTypeRepository.save(dictType), BizErrCode.FAIL, "保存失败");
    }

    @Override
    public void removeById(Serializable id) {
        Assert.isTrue(dictTypeRepository.removeById(id), BizErrCode.FAIL, "删除失败");
    }

    @Override
    public void updateById(DictTypeDTO dictTypeDTO) {
        DictType dictType = DictTypeAssembler.INSTANCE.dtoToEntity(dictTypeDTO);
        Assert.isTrue(dictTypeRepository.updateById(dictType), BizErrCode.FAIL, "修改失败");
    }

    @Override
    public DictTypeDTO getById(Serializable id) {
        DictType dictType = dictTypeRepository.getById(id);
        return DictTypeAssembler.INSTANCE.entityToDto(dictType);
    }

    @Override
    public PageResult<DictTypeDTO> page(DictTypeQuery query) {
        // @formatter:off
        query.checkPage();
        Page<DictType> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        dictTypeRepository.list(query);
        List<DictTypeDTO> dictTypeDTOs = page.getResult()
            .stream()
            .map(DictTypeAssembler.INSTANCE::entityToDto)
            .collect(Collectors.toList());
        return new PageResult<>(page.getPageNum(), page.getPageSize(), page.getTotal(), dictTypeDTOs);
        // @formatter:on
    }
}
