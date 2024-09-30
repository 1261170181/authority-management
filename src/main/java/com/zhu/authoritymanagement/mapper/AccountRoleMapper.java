package com.zhu.authoritymanagement.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.zhu.authoritymanagement.entity.AccountRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.authoritymanagement.vo.RoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@InterceptorIgnore(dataPermission = "true")
public interface AccountRoleMapper extends BaseMapper<AccountRole> {
    /**
     * 根据账号id查询用户角色
     *
     * @param accountId 账号id
     * @return 用户角色
     */
    AccountRole findByAccountId(@Param("accountId") Long accountId);

    /**
     * 删除账号对应角色
     *
     * @param accountId 账号id
     * @return 是否删除成功
     */
    int deleteRoleAccount(@Param("accountId") Long accountId);

    /**
     * 根据用户名查找对应角色
     *
     * @param username 用户名
     * @return 角色列表
     */
    List<RoleVO> findRolesByUsername(@Param("username") String username);
}
