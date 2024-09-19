package com.zhu.authoritymanagement.controller;

import com.zhu.authoritymanagement.entity.Role;
import com.zhu.authoritymanagement.service.IRoleService;
import com.zhu.authoritymanagement.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/list")
    @ResponseBody
    public Response<List<Role>> listRole() {
        List<Role> role = roleService.list();
        return Response.ok(role);
    }

    @PostMapping("/add")
    @ResponseBody
    public Response<Object> addRole(@RequestBody Role role) {
        return Response.buildResult(roleService.saveRole(role));
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public Response<Object> updateRole(@PathVariable Long id,@RequestBody Role role) {
        role.setRoleId(id);
        return Response.buildResult(roleService.updateRole(role));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Response<Object> deleteRole(@PathVariable Long id) {
        boolean success = roleService.removeById(id);
        return Response.buildResult(success);
    }

}