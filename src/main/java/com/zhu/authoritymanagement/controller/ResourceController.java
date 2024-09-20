package com.zhu.authoritymanagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhu.authoritymanagement.entity.Resource;
import com.zhu.authoritymanagement.service.IResourceService;
import com.zhu.authoritymanagement.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    private IResourceService resourceService;

    @Autowired
    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/list")
    @ResponseBody
    public Response<List<Resource>> listResource() {
        return Response.ok(resourceService.list());
    }

    @PostMapping("/add")
    @ResponseBody
    public Response<Object> addResource(@RequestBody Resource resource) {
        LambdaQueryWrapper<Resource> queryWrapper = Wrappers.<Resource>lambdaQuery()
                .eq(Resource::getResourceName, resource.getResourceName());
        if (resourceService.count(queryWrapper) > 0) {
            return Response.failed("权限名重复");
        }
        queryWrapper = Wrappers.<Resource>lambdaQuery()
                .eq(Resource::getUrl, resource.getUrl());
        if (resourceService.count(queryWrapper) > 0) {
            return Response.failed("权限内容重复");
        }
        if (!resource.getUrl().endsWith("/")) {
            return Response.failed("Url必须以/结尾");
        }
        return Response.buildResult(resourceService.save(resource));
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public Response<Object> updateResource(@PathVariable Long id, @RequestBody Resource resource) {
        if (resourceService.getById(id) == null) {
            return Response.failed("权限不存在");
        }
        resource.setResourceId(id);
        return Response.buildResult(resourceService.updateById(resource));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Response<Object> deleteResource(@PathVariable Long id) {
        if (resourceService.getById(id) == null) {
            return Response.failed("权限不存在");
        }
        if (id == 1) {
            return Response.failed("不能删除root权限");
        }
        return Response.buildResult(resourceService.removeById(id));
    }

}
