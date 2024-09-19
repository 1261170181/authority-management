package com.zhu.authoritymanagement.controller;

import com.zhu.authoritymanagement.dto.AccountDTO;
import com.zhu.authoritymanagement.entity.Account;
import com.zhu.authoritymanagement.service.IAccountService;
import com.zhu.authoritymanagement.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 账号表 前端控制器
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    private IAccountService accountService;

    @Autowired
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 获取账号列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Response<List<AccountDTO>> listAccount() {
        List<AccountDTO> accountDTO = accountService.listAccount();
        return Response.ok(accountDTO);
    }

    /**
     * 添加账号
     */
    @PostMapping("/add")
    @ResponseBody
    public Response<Object> addAccount(@RequestBody Account account) {
        accountService.setPasswordAndSalt(account);
        return Response.buildR(accountService.save(account));
    }

    /**
     * 更新账号信息
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public Response<Object> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        account.setAccountId(id);
        return Response.buildR(accountService.updateById(account));
    }

    /**
     * 设置账号角色
     */
    @PutMapping("/setrole/{id}")
    @ResponseBody
    public Response<Object> setRoleAccount(@PathVariable Long id, @RequestBody Long roleId) {
        return Response.buildR(accountService.setRoleAccount(id, roleId));
    }

    /**
     * 删除账号
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Response<Object> delete(@PathVariable Long id, HttpSession session) {
        if (session != null) {
            Account account = (Account) session.getAttribute("account");
            if (account.getAccountId().equals(id)) {
                return Response.failed("不能删除自己");
            }
        }
        return Response.buildR(accountService.removeById(id));
    }

}
