package com.zhu.authoritymanagement.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.zhu.authoritymanagement.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@InterceptorIgnore(dataPermission = "true")
public interface RoleMapper extends BaseMapper<Role> {

}
