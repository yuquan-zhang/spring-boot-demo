package com.zhang.yong.demothree.module.admin.controller;

import com.github.pagehelper.Page;
import com.zhang.yong.demothree.module.admin.AdminBaseController;
import com.zhang.yong.demothree.module.admin.bean.Role;
import com.zhang.yong.demothree.module.admin.service.RoleService;
import com.zhang.yong.demothree.util.PageUtil;
import com.zhang.yong.demothree.vo.JsonObject;
import com.zhang.yong.demothree.vo.PageObject;
import com.zhang.yong.demothree.vo.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(AdminBaseController.ADMIN + "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("index")
    public String index() {
        return "admin/role/index.html";
    }

    @GetMapping("/all")
    @ResponseBody
    public PageObject getUsers(@RequestParam Map<String,Object> map) {
        PageParam pageParam = PageParam.instance(map);
        Page<Role> pageInfo = PageUtil.startPage(pageParam);
        roleService.getAll();
        return PageObject.page(pageInfo,pageParam.getDraw());
    }

    @GetMapping("addOrEdit")
    public String addOrEdit(Long id, Model model) {
        if(null != id) {
            model.addAttribute("role",roleService.getById(id));
        }else{
            model.addAttribute("role",new Role());
        }
        return "admin/role/addOrEdit.html";
    }

    @PostMapping("saveOrUpdate")
    @ResponseBody
    public JsonObject saveOrUpdate(@RequestBody Role role) {
        try{
            roleService.saveOrUpdate(role);
            return JsonObject.success("保存或更新角色成功");
        }catch (Exception e) {
            e.printStackTrace();
            return JsonObject.error("保存或更新角色失败");
        }
    }

    @PostMapping("checkNameUnique")
    @ResponseBody
    public boolean checkNameUnique(String name, Long id) {
        Role role = roleService.getByName(name);
        return role == null || (id != null && id.equals(role.getId()));
    }

}
