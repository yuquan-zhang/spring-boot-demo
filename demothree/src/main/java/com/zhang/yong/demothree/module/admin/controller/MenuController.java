package com.zhang.yong.demothree.module.admin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhang.yong.demothree.module.admin.AdminBaseController;
import com.zhang.yong.demothree.module.admin.bean.Menu;
import com.zhang.yong.demothree.module.admin.service.MenuService;
import com.zhang.yong.demothree.util.CollectionUtil;
import com.zhang.yong.demothree.util.PageUtil;
import com.zhang.yong.demothree.vo.JsonObject;
import com.zhang.yong.demothree.vo.PageObject;
import com.zhang.yong.demothree.vo.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(AdminBaseController.ADMIN + "/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/all")
    @ResponseBody
    public PageObject getAll(@RequestParam Map<String,Object> map) {
        PageParam pageParam = PageParam.instance(map);
        Page<Menu> pageInfo = PageUtil.startPage(pageParam);
        menuService.getAll();
        return PageObject.page(pageInfo,pageParam.getDraw());
    }

    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("menus", menuService.getTreeTableMenu());
        return "admin/menu/index.html";
    }

    @GetMapping("addOrEdit")
    public String addOrEdit(Long id, Model model) {
        if(null != id) {
            Menu menu = menuService.getById(id);
            model.addAttribute("menu",menu);
            model.addAttribute("parent",menuService.getById(menu.getParentId()));
        }else{
            model.addAttribute("menu",new Menu());
        }
        return "admin/menu/addOrEdit.html";
    }

    @PostMapping("saveOrUpdate")
    @ResponseBody
    public JsonObject saveOrUpdate(@RequestBody Menu menu) {
        try{
            menuService.saveOrUpdate(menu);
            return JsonObject.success("保存或更新菜单成功");
        }catch (Exception e) {
            e.printStackTrace();
            return JsonObject.error("保存或更新菜单失败");
        }
    }

    @PostMapping("fetchMenuTree")
    @ResponseBody
    public List<Map<String,Object>> fetchMenuTree(Long roleId) {
        List<Menu> allMenus = menuService.getAll();
        List<Menu> bindMenus = menuService.getMenusByRoleId(roleId);
        List<Map<String,Object>> result = new ArrayList<>();
        if(CollectionUtil.notEmpty(allMenus)) {
            for(Menu menu : allMenus) {
                Map<String, Object> map = new HashMap<>();
                map.put("id",menu.getId());
                map.put("pId",menu.getParentId());
                map.put("name",menu.getName());
                map.put("type",menu.isType());
                if(CollectionUtil.notEmpty(bindMenus) && bindMenus.contains(menu)) {
                    map.put("checked",true);
                }
                result.add(map);
            }
        }
        return result;
    }
}
