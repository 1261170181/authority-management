package com.zhu.authoritymanagement.controller;

import com.zhu.authoritymanagement.aop.ControllerWebLog;
import com.zhu.authoritymanagement.dto.LoginDTO;
import com.zhu.authoritymanagement.entity.Account;
import com.zhu.authoritymanagement.entity.Resource;
import com.zhu.authoritymanagement.service.IAccountService;
import com.zhu.authoritymanagement.service.IResourceService;
import com.zhu.authoritymanagement.util.Response;
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
import java.util.HashSet;
import java.util.List;

/**
 * 登录控制器
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Controller
@RequestMapping("auth")
public class LoginController {
    private IAccountService accountService;

    private IResourceService resourceService;

    @Autowired
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 登录
     */
    @ControllerWebLog(name = "登录")
    @PostMapping("/login")
    @ResponseBody
    public Response<String> login(@RequestBody Account account,HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account.getUsername(), account.getPassword());
        try {
            subject.login(token);
            session.setAttribute("user", token.getPrincipal());
            return Response.ok(null);
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
    public Response<String> logout(HttpSession session) {
        Object currentAccount =session.getAttribute("account");
        if (currentAccount == null) {
            return Response.failed("尚未登录");
        }
        session.invalidate();
        return Response.ok("redirect:/auth/login");
    }
}
