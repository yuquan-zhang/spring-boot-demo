package com.zhang.yong.demothree.module.admin.controller;

import com.zhang.yong.demothree.module.admin.AdminBaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(AdminBaseController.ADMIN)
public class IndexController{

    private final static Logger log = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("index")
    public String index(Model model, HttpSession session) {
        session.setAttribute("currentUser",SecurityUtils.getSubject().getPrincipal());
        return "admin/index.html";
    }

    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("message","欢迎您的到来，我们等你很久了");
        return "admin/home.html";
    }

    @RequestMapping("login")
    public String login() {
        return "admin/login.html";
    }

    @PostMapping(value="login")
    public String doLogin(String username, String password, boolean rememberMe, RedirectAttributes redirectAttributes) {
        String errorMsg = "";
        if (StringUtils.isEmpty(username)){
            errorMsg = "用户名不能为空";
        }
        if (StringUtils.isEmpty(password)){
            errorMsg = "用户名不能为空";
        }
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //登录
            if(StringUtils.isEmpty(errorMsg)){
                UsernamePasswordToken token = new UsernamePasswordToken(username,password);
                token.setRememberMe(rememberMe);
                currentUser.login(token);
                return "redirect:/admin/index";
            }
        } catch ( UnknownAccountException uae ) {
            log.warn("用户帐号不正确");
            errorMsg = "用户帐号或密码不正确";

        } catch ( IncorrectCredentialsException ice ) {
            log.warn("用户密码不正确");
            errorMsg = "用户帐号或密码不正确";

        } catch ( LockedAccountException lae ) {
            log.warn("用户帐号被锁定");
            errorMsg = "用户帐号被锁定不可用";

        } catch ( AuthenticationException ae ) {
            log.warn("登录出错");
            errorMsg = "登录失败："+ae.getMessage();
        }
        redirectAttributes.addFlashAttribute("username",username);
        redirectAttributes.addFlashAttribute("password",password);
        redirectAttributes.addFlashAttribute("errorMsg",errorMsg);
        return "redirect:/admin/login";
    }

    @GetMapping("logout")
    public String logout(RedirectAttributes redirectAttributes) {
        try {
            SecurityUtils.getSubject().logout();
            return "redirect:/admin/login";
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg",e.getLocalizedMessage());
            return "redirect:/admin/login";
        }
    }

    @RequestMapping("unauthc")
    public String unauthc(Model model) {
        return "admin/unauthc.html";
    }
}
