package com.nuite.manager.mapper;

import com.nuite.manager.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> listAllParentMenu();

    List<Menu> listSubMenuByParentId(Integer parentId);

    Menu getMenuById(Integer menuId);

    void insertMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenuById(Integer menuId);

    List<Menu> listAllSubMenu();
}
