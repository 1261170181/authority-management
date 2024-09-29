package com.zhu.authoritymanagement.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.zhu.authoritymanagement.entity.RoleResource;

/**
 * <p>
 * 角色权限表 Mapper 接口
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@InterceptorIgnore(dataPermission = "true")
public interface RoleResourceMapper extends MyBaseMapper<RoleResource> {
}
