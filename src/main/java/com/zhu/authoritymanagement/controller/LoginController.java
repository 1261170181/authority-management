package com.zhu.authoritymanagement.controller;

import com.zhu.authoritymanagement.aop.ControllerWebLog;
import com.zhu.authoritymanagement.service.IAccountService;
import com.zhu.authoritymanagement.vo.RoleVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 登录控制器
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Controller
public class LoginController {

    private IAccountService accountService;

    @Autowired
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }


    /**
     * 显示登录页面
     */
    @ControllerWebLog(name = "显示登录页面")
    @GetMapping("/login")
    public String loginPage() {
        return "main/login";
    }

    /**
     * 显示无权限页面
     */
    @ControllerWebLog(name = "显示无权限页面")
    @GetMapping("/unAuth")
    public String unAuthPage() {
        return "main/unAuth";
    }

    /**
     * 显示用户主页
     */
    @ControllerWebLog(name = "显示用户主页")
    @GetMapping("/main")
    public String userPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("account");
        List<RoleVO> roles = accountService.findRolesByUsername(username);
        String roleName = roles.isEmpty() ? "无角色" : roles.get(0).getRoleName();
        session.setAttribute("roleName", roleName);
        model.addAttribute("username", username);
        model.addAttribute("roleName", roleName);
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
