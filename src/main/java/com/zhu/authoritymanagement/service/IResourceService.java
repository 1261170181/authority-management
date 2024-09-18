package com.zhu.authoritymanagement.service;

import com.zhu.authoritymanagement.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
public interface IResourceService extends IService<Resource> {
    List<Resource> listResourceByRoleId(Long roleId);
    HashSet<String> convert(List<Resource> resources);
}
