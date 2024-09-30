package com.zhu.authoritymanagement.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.MD5;
import com.zhu.authoritymanagement.vo.AccountVO;
import com.zhu.authoritymanagement.entity.Account;
import com.zhu.authoritymanagement.entity.AccountRole;
import com.zhu.authoritymanagement.mapper.AccountMapper;
import com.zhu.authoritymanagement.mapper.AccountRoleMapper;
import com.zhu.authoritymanagement.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.authoritymanagement.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private AccountRoleMapper accountRoleMapper;

    private AccountMapper accountMapper;

    @Autowired
    public void setAccountRoleMapper(AccountRoleMapper accountRoleMapper) {
        this.accountRoleMapper = accountRoleMapper;
    }

    @Autowired
    public void setAccountMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public List<AccountVO> listAccount() {
        return accountMapper.listAccount();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setPasswordAndSalt(Account account) {
        String password = account.getPassword();
        String salt = UUID.fastUUID().toString().replaceAll("-", "");
        MD5 md5 = new MD5(salt.getBytes());
        String digestHex = md5.digestHex(password);
        account.setPassword(digestHex);
        account.setSalt(salt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setRoleAccount(Long id, Long roleId) {
        AccountRole accountRole = new AccountRole();
        accountRole.setAccountId(id);
        accountRole.setRoleId(roleId);
        accountRoleMapper.insert(accountRole);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRoleAccount(Long id) {
        int deleted = accountRoleMapper.deleteRoleAccount(id);
        return deleted > 0;
    }

    @Override
    public List<RoleVO> findRolesByUsername(String username) {
        return accountRoleMapper.findRolesByUsername(username);
    }

}
