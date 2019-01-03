package com.zhang.yong.demothree.module.admin.service;

import com.zhang.yong.demothree.module.admin.bean.Menu;
import com.zhang.yong.demothree.module.admin.dao.MenuMapper;
import com.zhang.yong.demothree.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Menu> getAll() {
        return menuMapper.selectAll();
    }

    public Menu getById(Long id) {
        return menuMapper.selectById(id);
    }

    public List<Menu> getByType(int type) {
        return menuMapper.selectByType(type);
    }

    public Set<String> getPermissionsByUserId(Long userId) {
        return menuMapper.selectByUserId(userId);
    }

    public List<Menu> getMenusByRoleId(Long roleId) {
        return menuMapper.selectByRoleId(roleId);
    }

    public List<Menu> getMenusByUserId(Long userId) {
        return menuMapper.selectMenusByUserId(userId);
    }

    /**
     * 获取当前用户所有的按钮权限，URL菜单，和URL菜单的所有层级的父菜单
     * @param menus 当前用户的所有的按钮菜单和URL菜单
     * @param allMenus 系统中所有的URL菜单
     * @param resultMenus 当前用户的URL菜单和所有层级的父菜单
     * @return 按钮权限集合
     */
    public Set<String> getPermissions(List<Menu> menus, List<Menu> allMenus, List<Menu> resultMenus) {
        Set<String> permissions = new HashSet<>();
        Set<Long> longs = new HashSet<>();
        for(Menu menu : menus) {
            if(menu.isType()) {
                permissions.add(menu.getPermission());
            }else{
                resultMenus.add(menu);
                longs.add(menu.getId());
                addParentMenu(menu.getParentId(),allMenus,resultMenus,longs);
            }
        }
        return permissions;
    }

    /**
     * 递归获取所有层级的父菜单，一直到最顶级父菜单
     * @param menuId 父菜单ID
     * @param allMenus 系统中所有的URL菜单
     * @param resultMenus 当前用户的URL菜单和所有层级的父菜单
     * @param longs 父菜单ID集合，用于快速判断当前父菜单是否已在结果集合中，以减少程序递归次数和深度
     */
    private void addParentMenu(Long menuId, List<Menu> allMenus, List<Menu> resultMenus,Set<Long> longs) {
        if(menuId == null || ObjectUtils.isEmpty(allMenus) || menuId.equals(0L) || longs.contains(menuId)) return;
        Menu menu = getInListById(allMenus,menuId);
        if(ObjectUtils.isEmpty(menu)) return;
        resultMenus.add(menu);
        longs.add(menu.getId());
        addParentMenu(menu.getParentId(),allMenus,resultMenus,longs);
    }

    /**
     * 根据ID获取菜单对象实例
     * @param menus 系统中所有的URL菜单
     * @param id 菜单ID
     * @return 菜单对象实例
     */
    private Menu getInListById(List<Menu> menus, Long id) {
        if(menus == null || id == null) return null;
        for(Menu menu : menus) {
            if(id.equals(menu.getId())) return menu;
        }
        return null;
    }

    /**
     * 将菜单集合转为树形结构
     * @param menus URL菜单集合
     * @return 树形结构URL菜单集合 最顶级菜单作为树的根节点，根节点菜单的父ID为0L
     */
    public List<Menu> transformMenusToTree(List<Menu> menus) {
        List<Menu> topMenus = new ArrayList<>();
        for (Menu menu : menus) {
            if(menu.getParentId().equals(0L)) {
                setSubMenus(menus,menu);
                topMenus.add(menu);
            }
        }
        return topMenus;
    }

    /**
     * 递归设置每一级父菜单的所有子菜单集合
     * @param menus URL菜单集合
     * @param parentMenu 父菜单
     */
    private void setSubMenus(List<Menu> menus,Menu parentMenu) {
        List<Menu> subMenus = new ArrayList<>();
        for(Menu menu : menus) {
            if (parentMenu.getId().equals(menu.getParentId())) {
                subMenus.add(menu);
                setSubMenus(menus,menu);
            }
        }
        parentMenu.setSubMenus(subMenus);
    }

    /**
     * 将所有菜单按照树形表需要的顺序重新排序
     * @return 重新排序后的菜单集合
     */
    public List<Menu> getTreeTableMenu() {
        List<Menu> allMenus = getAll();
        List<Menu> topMenus = transformMenusToTree(allMenus);
        List<Menu> reordered = new ArrayList<>();
        transToTreeTable(topMenus,reordered);
        return reordered;
    }

    /**
     * 递归添加菜单及其子菜单
     * @param topMenus 子菜单集合
     * @param reordered 重新排序后的菜单集合
     */
    private void transToTreeTable(List<Menu> topMenus,List<Menu> reordered) {
        if(CollectionUtil.notEmpty(topMenus)) {
            for(Menu menu : topMenus) {
                reordered.add(menu);
                transToTreeTable(menu.getSubMenus(),reordered);
            }
        }
    }

    public void saveOrUpdate(Menu menu) {
        if(null == menu.getParentId()) {
            menu.setParentId(0L);
        }
        if(null == menu.getId()) {
            menuMapper.insert(menu);
        }else{
            menuMapper.update(menu);
        }
    }
}
