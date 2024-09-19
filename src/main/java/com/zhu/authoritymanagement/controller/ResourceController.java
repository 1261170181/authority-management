package com.zhu.authoritymanagement.controller;

import com.zhu.authoritymanagement.entity.Resource;
import com.zhu.authoritymanagement.entity.Role;
import com.zhu.authoritymanagement.service.IAccountService;
import com.zhu.authoritymanagement.service.IResourceService;
import com.zhu.authoritymanagement.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
        List<Resource> resource = resourceService.list();
        return Response.ok(resource);
    }
}
