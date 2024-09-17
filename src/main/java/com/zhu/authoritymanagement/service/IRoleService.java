package com.zhu.authoritymanagement.service;

import com.zhu.authoritymanagement.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
public interface IRoleService extends IService<Role> {
    public boolean saveRole(Role role);

    public boolean updateRole(Role role);

}
