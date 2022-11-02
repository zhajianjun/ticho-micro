package com.ticho.upms.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.boot.web.util.CloudIdUtil;
import com.ticho.boot.web.util.valid.ValidGroup;
import com.ticho.boot.web.util.valid.ValidUtil;
import com.ticho.upms.application.service.DictTypeService;
import com.ticho.upms.domain.repository.DictRepository;
import com.ticho.upms.domain.repository.DictTypeRepository;
import com.ticho.upms.infrastructure.entity.DictType;
import com.ticho.upms.interfaces.assembler.DictTypeAssembler;
import com.ticho.upms.interfaces.dto.DictTypeDTO;
import com.ticho.upms.interfaces.query.DictTypeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private DictRepository dictRepository;

    @Override
    public void save(DictTypeDTO dictTypeDTO) {
        ValidUtil.valid(dictTypeDTO, ValidGroup.Add.class);
        this.isRepeatDictTypePreCheck(dictTypeDTO, true);
        DictType dictType = DictTypeAssembler.INSTANCE.dtoToEntity(dictTypeDTO);
        dictType.setId(CloudIdUtil.getId());
        Assert.isTrue(dictTypeRepository.save(dictType), BizErrCode.FAIL, "保存失败");
    }

    @Override
    public void removeById(Long id) {
        Assert.isNotEmpty(id, BizErrCode.PARAM_ERROR, "编号不能为空");
        boolean existsDict = dictRepository.existsByTypeId(id);
        Assert.isTrue(!existsDict, BizErrCode.PARAM_ERROR, "删除失败，请先删除所有字典");
        Assert.isTrue(dictTypeRepository.removeById(id), BizErrCode.FAIL, "删除失败");
    }

    @Override
    public void updateById(DictTypeDTO dictTypeDTO) {
        Assert.isNotEmpty(dictTypeDTO.getId(), BizErrCode.PARAM_ERROR, "编号不能为空");
        this.isRepeatDictTypePreCheck(dictTypeDTO, false);
        DictType dictType = DictTypeAssembler.INSTANCE.dtoToEntity(dictTypeDTO);
        Assert.isTrue(dictTypeRepository.updateById(dictType), BizErrCode.FAIL, "修改失败");
    }

    @Override
    public DictTypeDTO getById(Long id) {
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

    /**
     * 数据字典是否重复预校验
     */
    private void isRepeatDictTypePreCheck(DictTypeDTO dictTypeDTO, boolean saveOrUpdate) {
        String selectCode = null;
        if (!saveOrUpdate) {
            LambdaQueryWrapper<DictType> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.select(DictType::getId, DictType::getCode);
            queryWrapper.eq(DictType::getId, dictTypeDTO.getId());
            DictType select = dictTypeRepository.getOne(queryWrapper);
            selectCode = select.getCode();
        }
        LambdaQueryWrapper<DictType> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DictType::getCode, dictTypeDTO.getCode());
        queryWrapper.ne(selectCode != null, DictType::getCode, selectCode);
        Assert.isTrue(dictTypeRepository.count(queryWrapper) == 0, BizErrCode.FAIL, "数据字典类型重复");
    }
}
