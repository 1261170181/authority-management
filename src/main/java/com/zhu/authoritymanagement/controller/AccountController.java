package com.zhu.authoritymanagement.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhu.authoritymanagement.dto.AccountDTO;
import com.zhu.authoritymanagement.entity.Account;
import com.zhu.authoritymanagement.service.IAccountService;
import com.zhu.authoritymanagement.service.IRoleService;
import com.zhu.authoritymanagement.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/list")
    @ResponseBody
    public Response<List<AccountDTO>> listAccount() {
        LambdaQueryWrapper<Account> wrapper = Wrappers.<Account>lambdaQuery()
                .select(Account::getAccountId, Account::getUsername);
        List<Account> accounts = accountService.list(wrapper);

        List<AccountDTO> accountDTO = accounts.stream().map(account -> {
            AccountDTO dto = new AccountDTO();
            dto.setId(account.getAccountId());
            dto.setUsername(account.getUsername());
            return dto;
        }).collect(Collectors.toList());

        return Response.ok(accountDTO);
    }

    private void setPasswordAndSalt(Account account) {
        String password = account.getPassword();
        String salt = UUID.fastUUID().toString().replaceAll("-", "");
        MD5 md5 = new MD5(salt.getBytes());
        String digestHex = md5.digestHex(password);
        account.setPassword(digestHex);
        account.setSalt(salt);
    }

    @PostMapping("/add")
    @ResponseBody
    public Response<Object> addAccount(@RequestBody Account account) {
        setPasswordAndSalt(account);
        return Response.buildR(accountService.save(account));
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public Response<Object> updateAccount(@PathVariable Long id,@RequestBody Account account) {
        account.setAccountId(id);
        return Response.buildR(accountService.updateById(account));
    }

    @PutMapping("/setrole/{id}")
    @ResponseBody
    public Response<Object> setRoleAccount(@PathVariable Long id,@RequestBody Long roleId) {
        return Response.buildR(accountService.setRoleAccount(id,roleId));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Response<Object> delete(@PathVariable Long id, HttpSession session) {
        if (session != null) {
        Account account = (Account) session.getAttribute("account");
        if (account.getAccountId().equals(id)) {
            return Response.failed("不能删除自己");
        }}
        return Response.buildR(accountService.removeById(id));
    }

}
