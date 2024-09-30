package com.zhu.authoritymanagement.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.zhu.authoritymanagement.entity.Role;
import com.zhu.authoritymanagement.entity.RoleResource;
import com.zhu.authoritymanagement.mapper.RoleMapper;
import com.zhu.authoritymanagement.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhu.authoritymanagement.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    private RoleMapper roleMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRole(Role role) {
        List<RoleResource> roleResources = createRoleResources(role);
        roleMapper.saveRole(role, roleResources);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(Role role) {
        List<RoleResource> roleResources = createRoleResources(role);
        roleMapper.updateRole(role, roleResources);
        return true;
    }

    private List<RoleResource> createRoleResources(Role role) {
        List<Long> resourceIds = role.getResourceIds();
        List<RoleResource> roleResources = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(resourceIds)) {
            for (Long resourceId : resourceIds) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(role.getRoleId());
                roleResource.setResourceId(resourceId);
                roleResources.add(roleResource);
            }
        }

        return roleResources;
    }

    @Override
    public List<RoleVO> listRole() {
        return roleMapper.listRole();
    }

}
