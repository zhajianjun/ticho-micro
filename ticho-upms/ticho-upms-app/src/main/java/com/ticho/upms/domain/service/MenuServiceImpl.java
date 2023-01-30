package com.ticho.upms.domain.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.upms.application.service.MenuService;
import com.ticho.upms.domain.repository.MenuFuncRepository;
import com.ticho.upms.domain.repository.MenuRepository;
import com.ticho.upms.domain.repository.RoleFuncRepository;
import com.ticho.upms.infrastructure.core.util.TreeUtil;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.infrastructure.entity.MenuFunc;
import com.ticho.upms.interfaces.assembler.MenuAssembler;
import com.ticho.upms.interfaces.dto.FuncDTO;
import com.ticho.upms.interfaces.dto.MenuDTO;
import com.ticho.upms.interfaces.dto.MenuFuncDTO;
import com.ticho.upms.interfaces.dto.MenuFuncDtlDTO;
import com.ticho.upms.interfaces.query.MenuQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Autowired
    private MenuFuncRepository menuFuncRepository;

    @Autowired
    private RoleFuncRepository roleFuncRepository;

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
    public List<MenuFuncDtlDTO> listAll(boolean containFunc) {
        // @formatter:off
        List<Menu> list = menuRepository.list();
        List<Long> menuIds = new ArrayList<>();
        List<MenuFuncDtlDTO> menuFuncDtls = list
            .stream()
            .peek(x-> menuIds.add(x.getId()))
            .sorted(Comparator.comparing(Menu::getParentId).thenComparing(Comparator.nullsLast(Comparator.comparing(Menu::getSort))))
            .map(MenuAssembler.INSTANCE::entityToDtlDto)
            .collect(Collectors.toList());
        if (containFunc) {
            Map<Long, List<FuncDTO>> menuFuncMap = menuFuncRepository.listByMenuIds(menuIds);
            return TreeUtil.tree(menuFuncDtls, 0L, x -> setFuncs(menuFuncMap, x));
        }
        return TreeUtil.tree(menuFuncDtls, 0L, null);
        // @formatter:on
    }

    private void setFuncs(Map<Long, List<FuncDTO>> menuFuncMap, MenuFuncDtlDTO menuFuncDtl) {
        Long menuId = menuFuncDtl.getId();
        List<FuncDTO> funcs = Optional.ofNullable(menuFuncMap.get(menuId)).orElseGet(ArrayList::new);
        List<Long> funcIds = new ArrayList<>();
        List<String> funcCodes = new ArrayList<>();
        for (FuncDTO func : funcs) {
            funcIds.add(func.getId());
            funcCodes.add(func.getCode());
        }
        menuFuncDtl.setFuncs(funcs);
        menuFuncDtl.setFuncIds(funcIds);
        menuFuncDtl.setFuncCodes(funcCodes);
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

    @Override
    public void saveFunc(MenuFuncDTO menuFuncDTO) {
        Long menuId = menuFuncDTO.getMenuId();
        List<Long> funcIds = menuFuncDTO.getFuncIds();
        List<MenuFunc> menuFuncs = menuFuncRepository.listByMenuId(menuId);
        List<Long> selectFuncIds = menuFuncs.stream().map(MenuFunc::getFuncId).collect(Collectors.toList());
        funcIds.removeAll(selectFuncIds);
        if (funcIds.isEmpty()) {
            return;
        }
        menuFuncs = funcIds.stream().map(x-> convertMenuFunc(menuId, x)).collect(Collectors.toList());
        menuFuncRepository.saveBatch(menuFuncs);
    }

    private MenuFunc convertMenuFunc(Long menuId, Long funcId) {
        MenuFunc menuFunc = new MenuFunc();
        menuFunc.setMenuId(menuId);
        menuFunc.setFuncId(funcId);
        return menuFunc;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFunc(MenuFuncDTO menuFuncDTO) {
        Long menuId = menuFuncDTO.getMenuId();
        List<Long> funcIds = menuFuncDTO.getFuncIds();
        menuFuncRepository.removeByMenuIdAndFuncIds(menuId, funcIds);
        roleFuncRepository.removeByMenuIdAndFuncIds(menuId, funcIds);
    }

}
