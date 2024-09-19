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

    /**
     * 查询对应角色所具有的资源
     * @param roleId 角色id
     * @return 资源列表
     */
    List<Resource> listResourceByRoleId(Long roleId);

    /**
     * 查询对应角色所具有的资源或者所有资源
     * @param roleId 角色id
     * @param flag 1:查询对应角色所具有的资源 0:查询所有资源
     * @return 资源列表
     */
    List<Resource> listResource(Long roleId, Integer flag);

    /**
     * 将资源列表转换为资源名称集合
     * @param resources 资源列表
     * @return 资源名称集合
     */
    HashSet<String> convert(List<Resource> resources);
}
