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

    /**
     * 保存角色
     * @param role 角色
     * @return 是否保存成功
     */
    boolean saveRole(Role role);

    /**
     * 更新角色
     * @param role 角色
     * @return 是否更新成功
     */
    boolean updateRole(Role role);

}
