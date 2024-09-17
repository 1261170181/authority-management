package com.zhu.authoritymanagement.controller;

import com.zhu.authoritymanagement.dto.LoginDTO;
import com.zhu.authoritymanagement.entity.Resource;
import com.zhu.authoritymanagement.service.IAccountService;
import com.zhu.authoritymanagement.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;

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
    public String login(String username, String password, HttpSession session, RedirectAttributes attributes, Model model) {
        LoginDTO loginDTO = accountService.login(username, password);
        String error = loginDTO.getError();
        if (error == null) {
            session.setAttribute("account", loginDTO.getAccount());
            List<Resource> resources = resourceService.listResourceByRoleId(loginDTO.getRoleId());
            model.addAttribute("resources", resources);

            HashSet<String> module = this.resourceService.convert(resources);
            session.setAttribute("module", module);
        } else {
            attributes.addFlashAttribute("error", error);
        }
        return loginDTO.getPath();
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
