package com.zhu.authoritymanagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhu.authoritymanagement.aop.ControllerWebLog;
import com.zhu.authoritymanagement.vo.AccountVO;
import com.zhu.authoritymanagement.entity.Account;
import com.zhu.authoritymanagement.entity.AccountRole;
import com.zhu.authoritymanagement.service.IAccountRoleService;
import com.zhu.authoritymanagement.service.IAccountService;
import com.zhu.authoritymanagement.service.IRoleService;
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

    private IRoleService roleService;

    private IAccountRoleService accountRoleService;

    @Autowired
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setAccountRoleService(IAccountRoleService accountRoleService) {
        this.accountRoleService = accountRoleService;
    }

    /**
     * 查询账号列表
     */
    @ControllerWebLog(name = "查询账号列表")
    @GetMapping("/list")
    @ResponseBody
    public Response<List<AccountVO>> listAccount() {
        List<AccountVO> accountVO = accountService.listAccount();
        return Response.ok(accountVO);
    }

    /**
     * 添加账号
     */
    @ControllerWebLog(name = "添加账号")
    @PostMapping("/add")
    @ResponseBody
    public Response<Object> addAccount(@RequestBody Account account) {
        LambdaQueryWrapper<Account> queryWrapper = Wrappers.<Account>lambdaQuery()
                .eq(Account::getUsername, account.getUsername());
        if (accountService.count(queryWrapper) > 0) {
            return Response.failed("用户名重复");
        }
        accountService.setPasswordAndSalt(account);
        boolean result = accountService.save(account);
        if (result) {
            long id = account.getAccountId();
            accountService.setRoleAccount(id, 2L);
        }
        return Response.buildResult(result);
    }

    /**
     * 更新账号信息
     */
    @ControllerWebLog(name = "更新账号信息")
    @PutMapping("/update/{id}")
    @ResponseBody
    public Response<Object> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        if (accountService.getById(id) == null) {
            return Response.failed("账号不存在");
        }
        account.setAccountId(id);
        return Response.buildResult(accountService.updateById(account));
    }

    /**
     * 设置账号绑定角色
     */
    @ControllerWebLog(name = "设置账号绑定角色")
    @PutMapping("/setrole/{id}")
    @ResponseBody
    public Response<Object> setRoleAccount(@PathVariable Long id, @RequestBody Long roleId) {
        if (roleId == null) {
            return Response.failed("请输入角色id");
        }
        if (roleService.getById(roleId) == null) {
            return Response.failed("角色不存在");
        }
        LambdaQueryWrapper<AccountRole> queryWrapper = Wrappers.<AccountRole>lambdaQuery()
                .eq(AccountRole::getAccountId, id);
        long count = accountRoleService.count(queryWrapper);
        if (count > 0) {
            return Response.failed("该账号已经绑定角色");
        }
        return Response.buildResult(accountService.setRoleAccount(id, roleId));
    }

    /**
     * 解除账号绑定角色
     */
    @ControllerWebLog(name = "解除账号绑定角色")
    @PutMapping("/deleterole/{id}")
    @ResponseBody
    public Response<Object> deleteRoleAccount(@PathVariable Long id) {
        LambdaQueryWrapper<AccountRole> queryWrapper = Wrappers.<AccountRole>lambdaQuery()
                .eq(AccountRole::getAccountId, id);
        long count = accountRoleService.count(queryWrapper);
        if (count == 0) {
            return Response.failed("该账号没有绑定任何角色");
        }
        return Response.buildResult(accountService.deleteRoleAccount(id));
    }

    /**
     * 删除账号
     */
    @ControllerWebLog(name = "删除账号")
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Response<Object> delete(@PathVariable Long id, HttpSession session) {
        if (accountService.getById(id) == null) {
            return Response.failed("账号不存在");
        }
        if (id == 1) {
            return Response.failed("不能删除root账号");
        }
        if (session != null) {
            Account currentAccount = (Account) session.getAttribute("account");
            if (currentAccount.getAccountId().equals(id)) {
                return Response.failed("不能删除自己");
            }
        }
        return Response.buildResult(accountService.removeById(id));
    }

}
