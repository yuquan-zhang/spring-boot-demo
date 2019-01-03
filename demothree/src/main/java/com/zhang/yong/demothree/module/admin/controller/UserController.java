package com.zhang.yong.demothree.module.admin.controller;

import com.github.pagehelper.Page;
import com.zhang.yong.demothree.module.admin.AdminBaseController;
import com.zhang.yong.demothree.module.admin.bean.User;
import com.zhang.yong.demothree.module.admin.service.UserService;
import com.zhang.yong.demothree.util.PageUtil;
import com.zhang.yong.demothree.vo.JsonObject;
import com.zhang.yong.demothree.vo.PageObject;
import com.zhang.yong.demothree.vo.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(AdminBaseController.ADMIN + "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @ResponseBody
    public PageObject getUsers(@RequestParam Map<String,Object> map) {
        PageParam pageParam = PageParam.instance(map);
        Page<User> pageInfo = PageUtil.startPage(pageParam);
        userService.getAll();
        return PageObject.page(pageInfo,pageParam.getDraw());
    }

    @GetMapping("index")
    public String index() {
        return "admin/user/index.html";
    }

    @GetMapping("addOrEdit")
    public String addOrEdit(Long id, Model model) {
        if(null != id) {
            model.addAttribute("user",userService.getById(id));
        }else{
            model.addAttribute("user",new User());
        }
        return "admin/user/addOrEdit.html";
    }

    @PostMapping("saveOrUpdate")
    @ResponseBody
    public JsonObject saveOrUpdate(@RequestBody User user) {
        try{
            userService.saveOrUpdate(user);
            return JsonObject.success("保存或更新用户成功");
        }catch (Exception e) {
            return JsonObject.error("保存或更新用户失败");
        }
    }

    @ResponseBody
    @PostMapping("checkUsernameUnique")
    public boolean checkUsernameUnique(String username, Long id) {
        User user = userService.getByName(username);
        return user == null || (id != null && id.equals(user.getId()));
    }

}
