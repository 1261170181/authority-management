package com.zhu.authoritymanagement.controller;

import com.zhu.authoritymanagement.aop.ControllerWebLog;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Controller
public class LoginController {

    /**
     * 显示登录页面
     */
    @ControllerWebLog(name = "显示登录页面")
    @GetMapping("/login")
    public String loginPage() {
        return "login/login";
    }

    /**
     * 显示用户主页
     */
    @ControllerWebLog(name = "显示用户主页")
    @GetMapping("/main")
    public String userPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("account");
        model.addAttribute("username", username);
        return "main/main";
    }


    /**
     * 登录
     */
    @ControllerWebLog(name = "登录")
    @PostMapping("/doLogin")
    public String login(String username, String password, HttpSession session, RedirectAttributes redirectAttributes) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error", "已登录");
            return "redirect:/login";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            session.setAttribute("account", token.getPrincipal());
            return "redirect:/main";
        } catch (Exception e) {
            if (e instanceof UnknownAccountException || e instanceof IncorrectCredentialsException) {
                redirectAttributes.addFlashAttribute("error", "用户名或密码错误");
                return "redirect:/login";
            }
            redirectAttributes.addFlashAttribute("error", "登录失败");
            return "redirect:/login";
        }
    }

    /**
     * 登出
     */
    @ControllerWebLog(name = "登出")
    @PutMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error", "尚未登录");
            return "redirect:/login";
        }
        subject.logout();
        return "redirect:/login";
    }

}
