package com.zhu.authoritymanagement.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
        public LoginDTO login(String username, String password) {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setPath("redirect:/");

            Account account=lambdaQuery().eq(Account::getUsername, username).one();
            if (account==null){
                loginDTO.setError("用户不存在");
                return loginDTO;
            }

            MD5 md5=new MD5(account.getSalt().getBytes());
            String digestHex = md5.digestHex(password);
            if (!digestHex.equals(account.getPassword())){
                loginDTO.setError("密码错误");
                return loginDTO;
            }

            AccountRole accountRole = accountRoleMapper.findByAccountId(account.getAccountId());
            loginDTO.setAccount(account);
            loginDTO.setRoleId(accountRole.getRoleId());
            return loginDTO;
        }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setRoleAccount(Long id,Long roleId) {
        if (roleId!=null){

                AccountRole accountRole = new AccountRole();
                accountRole.setAccountId(id);
                accountRole.setRoleId(roleId);
                accountRoleMapper.insert(accountRole);

        }
        return true;
    }
 /*   @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAccount(Account account) {
        Long accountId = account.getAccountId();

        updateById(account);

        System.out.println("account = " + account);
        System.out.println("account.getAccountId() = " + account.getAccountId());

        accountRoleMapper.delete(Wrappers.<AccountRole>lambdaQuery().eq(AccountRole::getAccountId, accountId));

        Long roleId = account.getRoleId();

        if (roleId!=null) {
            AccountRole accountRole = new AccountRole();
            accountRole.setAccountId(accountId);
            accountRole.setRoleId(roleId);
            accountRoleMapper.insert(accountRole);
        }

        return true;
    }*/

}
