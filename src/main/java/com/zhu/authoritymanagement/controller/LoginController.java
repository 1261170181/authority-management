package com.zhu.authoritymanagement.controller;

import com.zhu.authoritymanagement.aop.ControllerWebLog;
import com.zhu.authoritymanagement.entity.Account;
import com.zhu.authoritymanagement.util.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Controller
@RequestMapping("auth")
public class LoginController {

    /**
     * 显示登录页面
     */
    @ControllerWebLog(name = "显示登录页面")
    @GetMapping("/tologin")
    public String loginPage() {
        return "login/login";
    }

    /**
     * 显示用户主页
     */
    @ControllerWebLog(name = "显示用户主页")
    @GetMapping("/main")
    public String userPage() {
        return "main/main";
    }


    /**
     * 登录
     */
    @ControllerWebLog(name = "登录")
    @PostMapping("/login")
    @ResponseBody
    public Response<String> login(@RequestBody Account account,HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return Response.failed("用户已登录");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(account.getUsername(), account.getPassword());
        try {
            subject.login(token);
            session.setAttribute("account", token.getPrincipal());
            return Response.ok("redirect:/auth/main");
        } catch (Exception e) {
            if (e instanceof UnknownAccountException || e instanceof IncorrectCredentialsException) {
                return Response.failed("用户名或密码错误");
            }
            return Response.failed("登录失败");
        }
    }

    /**
     * 登出
     */
    @ControllerWebLog(name = "登出")
    @PutMapping("/logout")
    @ResponseBody
    public Response<String> logout() {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            return Response.failed("尚未登录");
        }
        subject.logout();
        return Response.ok("redirect:/auth/login");
    }

}
