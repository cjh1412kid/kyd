package com.nuite.manager.service.impl;

import com.nuite.manager.entity.Menu;
import com.nuite.manager.mapper.MenuMapper;
import com.nuite.manager.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    MenuMapper menuMapper;

    public void deleteMenuById(Integer menuId) {
        // TODO Auto-generated method stub
        menuMapper.deleteMenuById(menuId);
    }

    public Menu getMenuById(Integer menuId) {
        // TODO Auto-generated method stub
        return menuMapper.getMenuById(menuId);
    }

    public List<Menu> listAllParentMenu() {
        // TODO Auto-generated method stub
        return menuMapper.listAllParentMenu();
    }

    public void saveMenu(Menu menu) {
        // TODO Auto-generated method stub
        if (menu.getMenuId() != null && menu.getMenuId().intValue() > 0) {
            menuMapper.updateMenu(menu);
        } else {
            menuMapper.insertMenu(menu);
        }
    }

    public List<Menu> listSubMenuByParentId(Integer parentId) {
        // TODO Auto-generated method stub
        return menuMapper.listSubMenuByParentId(parentId);
    }

    public List<Menu> listAllMenu() {
        // TODO Auto-generated method stub
        List<Menu> rl = this.listAllParentMenu();
        for (Menu menu : rl) {
            List<Menu> subList = this.listSubMenuByParentId(menu.getMenuId());
            menu.setSubMenu(subList);
        }
        return rl;
    }

    public List<Menu> listAllSubMenu() {
        return menuMapper.listAllSubMenu();
    }

//	public MenuMapper getMenuMapper() {
//		return menuMapper;
//	}
//
//	public void setMenuMapper(MenuMapper menuMapper) {
//		this.menuMapper = menuMapper;
//	}

}
