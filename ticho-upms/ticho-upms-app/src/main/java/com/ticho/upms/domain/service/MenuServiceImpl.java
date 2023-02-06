package com.ticho.upms.domain.service;

import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.util.Assert;
import com.ticho.upms.application.service.MenuService;
import com.ticho.upms.domain.handle.UpmsHandle;
import com.ticho.upms.domain.repository.MenuRepository;
import com.ticho.upms.domain.repository.RoleMenuRepository;
import com.ticho.upms.infrastructure.core.util.TreeUtil;
import com.ticho.upms.infrastructure.entity.Menu;
import com.ticho.upms.interfaces.assembler.MenuAssembler;
import com.ticho.upms.interfaces.dto.MenuDTO;
import com.ticho.upms.interfaces.dto.MenuDtlDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 菜单信息 服务实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Service
public class MenuServiceImpl extends UpmsHandle implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Override
    public void save(MenuDTO menuDTO) {
        Menu menu = MenuAssembler.INSTANCE.dtoToEntity(menuDTO);
        Assert.isTrue(menuRepository.save(menu), BizErrCode.FAIL, "保存失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        Assert.isTrue(menuRepository.removeById(id), BizErrCode.FAIL, "删除失败");
        List<Long> menuIds = Collections.singletonList(id);
        roleMenuRepository.removeByMenuIds(menuIds);
    }

    @Override
    public void updateById(MenuDTO menuDTO) {
        Menu menu = MenuAssembler.INSTANCE.dtoToEntity(menuDTO);
        Assert.isTrue(menuRepository.updateById(menu), BizErrCode.FAIL, "修改失败");
    }

    @Override
    public MenuDTO getById(Long id) {
        Menu menu = menuRepository.getById(id);
        return MenuAssembler.INSTANCE.entityToDto(menu);
    }

    @Override
    public List<MenuDtlDTO> list() {
        List<Menu> menus = menuRepository.list();
        List<MenuDtlDTO> menuFuncDtls = getMenuDtls(menus, null, null);
        return TreeUtil.tree(menuFuncDtls, 0L, null);
    }

}
