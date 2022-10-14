package com.ticho.upms.domain.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.upms.application.service.MenuService;
import com.ticho.upms.domain.repository.MenuRepository;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.interfaces.assembler.MenuAssembler;
import com.ticho.upms.interfaces.dto.MenuDTO;
import com.ticho.upms.interfaces.query.MenuQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单信息 服务实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public void save(MenuDTO menuDTO) {
        Menu menu = MenuAssembler.INSTANCE.dtoToEntity(menuDTO);
        Assert.isTrue(menuRepository.save(menu), BizErrCode.FAIL, "保存失败");
    }

    @Override
    public void removeById(Serializable id) {
        Assert.isTrue(menuRepository.removeById(id), BizErrCode.FAIL, "删除失败");
    }

    @Override
    public void updateById(MenuDTO menuDTO) {
        Menu menu = MenuAssembler.INSTANCE.dtoToEntity(menuDTO);
        Assert.isTrue(menuRepository.updateById(menu), BizErrCode.FAIL, "修改失败");
    }

    @Override
    public MenuDTO getById(Serializable id) {
        Menu menu = menuRepository.getById(id);
        return MenuAssembler.INSTANCE.entityToDto(menu);
    }

    @Override
    public PageResult<MenuDTO> page(MenuQuery query) {
        // @formatter:off
        query.checkPage();
        Page<Menu> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        menuRepository.list(query);
        List<MenuDTO> menuDTOs = page.getResult()
            .stream()
            .map(MenuAssembler.INSTANCE::entityToDto)
            .collect(Collectors.toList());
        return new PageResult<>(page.getPageNum(), page.getPageSize(), page.getTotal(), menuDTOs);
        // @formatter:on
    }
}