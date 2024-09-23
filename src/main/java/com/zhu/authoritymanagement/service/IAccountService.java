package com.zhu.authoritymanagement.service;

import com.zhu.authoritymanagement.vo.AccountVO;
import com.zhu.authoritymanagement.dto.LoginDTO;
import com.zhu.authoritymanagement.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 账号表 服务类
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
public interface IAccountService extends IService<Account> {

    /**
     * 密码加密
     *
     * @param account 账号
     */
    void setPasswordAndSalt(Account account);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录信息
     */
    LoginDTO login(String username, String password);

    /**
     * 设置账号对应角色
     *
     * @param id     账号id
     * @param roleId 角色id
     * @return 是否设置成功
     */
    boolean setRoleAccount(Long id, Long roleId);

    /**
     * 设置账号对应角色
     *
     * @param id 账号id
     * @return 是否设置成功
     */
    boolean deleteRoleAccount(Long id);

    /**
     * 获取账号列表
     *
     * @return 账号列表
     */
    List<AccountVO> listAccount();
}
