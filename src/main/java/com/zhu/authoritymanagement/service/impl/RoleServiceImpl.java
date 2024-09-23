package com.zhu.authoritymanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhu.authoritymanagement.entity.Role;
import com.zhu.authoritymanagement.entity.RoleResource;
import com.zhu.authoritymanagement.mapper.RoleMapper;
import com.zhu.authoritymanagement.mapper.RoleResourceMapper;
import com.zhu.authoritymanagement.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.authoritymanagement.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            List<RoleResource> roleResources = new ArrayList<>();
            for (Long resourceId : resourceIds) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResources.add(roleResource);
            }
            roleResourceMapper.insertBatchSomeColumn(roleResources);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(Role role) {
        Long roleId = role.getRoleId();

        updateById(role);

        roleResourceMapper.delete(Wrappers.<RoleResource>lambdaQuery().eq(RoleResource::getRoleId, roleId));

        List<Long> resourceIds = role.getResourceIds();

        if (CollectionUtils.isNotEmpty(resourceIds)) {
            List<RoleResource> roleResources = new ArrayList<>();
            for (Long resourceId : resourceIds) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResources.add(roleResource);
            }
            roleResourceMapper.insertBatchSomeColumn(roleResources);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<RoleVO> listRole() {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
                .select(Role::getRoleId, Role::getRoleName, Role::getRemark);
        List<Role> roles = this.list(wrapper);

        return roles.stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            roleVO.setRoleId(role.getRoleId());
            roleVO.setRoleName(role.getRoleName());
            roleVO.setRemark(role.getRemark());
            return roleVO;
        }).collect(Collectors.toList());
    }

}
