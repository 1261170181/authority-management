package com.zhu.authoritymanagement.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhu.authoritymanagement.dto.AccountDTO;
import com.zhu.authoritymanagement.dto.LoginDTO;
import com.zhu.authoritymanagement.entity.Account;
import com.zhu.authoritymanagement.entity.AccountRole;
import com.zhu.authoritymanagement.mapper.AccountMapper;
import com.zhu.authoritymanagement.mapper.AccountRoleMapper;
import com.zhu.authoritymanagement.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 账号表 服务实现类
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Autowired
    private AccountRoleMapper accountRoleMapper;

    @Override
    public List<AccountDTO> listAccount() {
        LambdaQueryWrapper<Account> wrapper = Wrappers.<Account>lambdaQuery()
                .select(Account::getAccountId, Account::getUsername);
        List<Account> accounts = this.list(wrapper);

        return accounts.stream().map(account -> {
            AccountDTO dto = new AccountDTO();
            dto.setId(account.getAccountId());
            dto.setUsername(account.getUsername());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void setPasswordAndSalt(Account account) {
        String password = account.getPassword();
        String salt = UUID.fastUUID().toString().replaceAll("-", "");
        MD5 md5 = new MD5(salt.getBytes());
        String digestHex = md5.digestHex(password);
        account.setPassword(digestHex);
        account.setSalt(salt);
    }

    @Override
    public LoginDTO login(String username, String password) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPath("redirect:/");

        Account account = lambdaQuery().eq(Account::getUsername, username).one();
        if (account == null) {
            loginDTO.setError("用户不存在");
            return loginDTO;
        }

        MD5 md5 = new MD5(account.getSalt().getBytes());
        String digestHex = md5.digestHex(password);
        if (!digestHex.equals(account.getPassword())) {
            loginDTO.setError("密码错误");
            return loginDTO;
        }

        AccountRole accountRole = accountRoleMapper.findByAccountId(account.getAccountId());
        loginDTO.setAccount(account);
        loginDTO.setRoleId(accountRole.getRoleId());
        loginDTO.setPath("main/main");
        return loginDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setRoleAccount(Long id, Long roleId) {
        if (roleId != null) {

            AccountRole accountRole = new AccountRole();
            accountRole.setAccountId(id);
            accountRole.setRoleId(roleId);
            accountRoleMapper.insert(accountRole);

        }
        return true;
    }
}
