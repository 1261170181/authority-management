package com.zhu.authoritymanagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhu.authoritymanagement.entity.Resource;
import com.zhu.authoritymanagement.entity.Role;
import com.zhu.authoritymanagement.service.IResourceService;
import com.zhu.authoritymanagement.service.IRoleService;
import com.zhu.authoritymanagement.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    private IRoleService roleService;

    private IResourceService resourceService;

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 获取角色列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Response<List<Role>> listRole() {
        return Response.ok(roleService.list());
    }

    /**
     * 添加角色
     */
    @PostMapping("/add")
    @ResponseBody
    public Response<Object> addRole(@RequestBody Role role) {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.<Role>lambdaQuery()
                .eq(Role::getRoleName, role.getRoleName());
        if (roleService.count(queryWrapper) > 0) {
            return Response.failed("角色名重复");
        }
        return Response.buildResult(roleService.saveRole(role));
    }

    /**
     * 更新角色信息
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public Response<Object> updateRole(@PathVariable Long id, @RequestBody Role role) {
        if (roleService.getById(id) == null) {
            return Response.failed("角色不存在");
        }
        HashSet<Long> uniqueResourceIds = new HashSet<>();
        for (Long resourceId : role.getResourceIds()) {
            LambdaQueryWrapper<Resource> queryWrapper = Wrappers.<Resource>lambdaQuery()
                    .eq(Resource::getResourceId, resourceId);
            if (resourceService.count(queryWrapper) == 0) {
                return Response.failed("资源ID " + resourceId + " 不存在");
            }
            if (!uniqueResourceIds.add(resourceId)) {
                return Response.failed("资源ID " + resourceId + " 重复");
            }
        }
        role.setRoleId(id);
        return Response.buildResult(roleService.updateRole(role));
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Response<Object> deleteRole(@PathVariable Long id, HttpSession session) {
        if (roleService.getById(id) == null) {
            return Response.failed("角色不存在");
        }
        if (id == 1) {
            return Response.failed("不能删除root角色");
        }
        if (session != null) {
            Role currentRole = (Role) session.getAttribute("account");
            if (currentRole.getRoleId().equals(id)) {
                return Response.failed("不能删除自己的角色");
            }
        }
        return Response.buildResult(roleService.removeById(id));
    }

}