package com.zhu.authoritymanagement.controller;

import com.zhu.authoritymanagement.dto.LoginDTO;
import com.zhu.authoritymanagement.entity.Account;
import com.zhu.authoritymanagement.entity.Resource;
import com.zhu.authoritymanagement.service.IAccountService;
import com.zhu.authoritymanagement.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;

/**
 *
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

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody Account account, HttpSession session, RedirectAttributes attributes, Model model) {
        LoginDTO loginDTO = accountService.login(account.getUsername(), account.getPassword());
        String error = loginDTO.getError();
        if (error == null) {
            session.setAttribute("account", loginDTO.getAccount());
            List<Resource> resources = resourceService.listResourceByRoleId(loginDTO.getRoleId());
            model.addAttribute("resources", resources);

            HashSet<String> module = this.resourceService.convert(resources);
            session.setAttribute("module", module);
        } else {
            attributes.addFlashAttribute("error", error);
            return loginDTO.getError();
        }
        return loginDTO.getPath();
    }

    @PostMapping("/logout")
    @ResponseBody
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
