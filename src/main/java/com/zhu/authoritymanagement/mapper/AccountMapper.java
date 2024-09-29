package com.zhu.authoritymanagement.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.zhu.authoritymanagement.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.authoritymanagement.vo.AccountVO;
import org.apache.ibatis.annotations.Select;

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
    @InterceptorIgnore(dataPermission = "false")
    @Select("SELECT a.account_id AS id, a.username, r.role_name " +
            "FROM account a " +
            "LEFT JOIN account_role ar ON a.account_id = ar.account_id " +
            "LEFT JOIN role r ON ar.role_id = r.role_id")
    List<AccountVO> listAccount();
}
