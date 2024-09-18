package com.zhu.authoritymanagement.service;

import com.zhu.authoritymanagement.dto.LoginDTO;
import com.zhu.authoritymanagement.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 账号表 服务类
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
public interface IAccountService extends IService<Account> {

    LoginDTO login(String username, String password);
    boolean setRoleAccount(Long id,Long roleId);
}
