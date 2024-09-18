package com.zhu.authoritymanagement.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhu.authoritymanagement.entity.Role;
import com.zhu.authoritymanagement.entity.RoleResource;
import com.zhu.authoritymanagement.mapper.RoleMapper;
import com.zhu.authoritymanagement.mapper.RoleResourceMapper;
import com.zhu.authoritymanagement.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRole(Role role) {
        save(role);

        Long roleId = role.getRoleId();
        List<Long> resourceIds = role.getResourceIds();

        if (CollectionUtils.isNotEmpty(resourceIds)) {
            for (Long resourceId : resourceIds) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResourceMapper.insert(roleResource);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(Role role) {
        Long roleId = role.getRoleId();

        updateById(role);

        roleResourceMapper.delete(Wrappers.<RoleResource>lambdaQuery().eq(RoleResource::getRoleId, roleId));

        List<Long> resourceIds = role.getResourceIds();

        if (CollectionUtils.isNotEmpty(resourceIds)) {
            for (Long resourceId : resourceIds) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResourceMapper.insert(roleResource);
            }
        }

        return true;
    }

}
