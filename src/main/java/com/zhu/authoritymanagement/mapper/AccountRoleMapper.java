package com.zhu.authoritymanagement.mapper;

import com.zhu.authoritymanagement.entity.AccountRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
public interface AccountRoleMapper extends BaseMapper<AccountRole> {
    AccountRole findByAccountId(@Param("accountId") Long accountId);
}
