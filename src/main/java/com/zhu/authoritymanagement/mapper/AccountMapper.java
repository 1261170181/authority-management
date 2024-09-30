package com.zhu.authoritymanagement.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.zhu.authoritymanagement.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.authoritymanagement.vo.AccountVO;

import java.util.List;

/**
 * <p>
 * 账号表 Mapper 接口
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@InterceptorIgnore(dataPermission = "true")
public interface AccountMapper extends BaseMapper<Account> {

    /**
     * 查询账号列表
     *
     * @return 账号列表
     */
    List<AccountVO> listAccount();
}
