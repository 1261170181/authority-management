package com.zhu.authoritymanagement.service;

import com.zhu.authoritymanagement.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;

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
     *
     * @param roleId 角色id
     * @return 资源列表
     */
    List<Resource> listResourceByRoleId(Long roleId);

}
