package com.zhu.authoritymanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhu.authoritymanagement.entity.Resource;
import com.zhu.authoritymanagement.mapper.ResourceMapper;
import com.zhu.authoritymanagement.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Override
    public List<Resource> listResourceByRoleId(Long roleId) {
        QueryWrapper<Resource> query = Wrappers.<Resource>query();
        query.eq("rr.role_id", roleId).isNull("re.parent_id").orderByAsc("re.sort");
        return baseMapper.selectList(query);
    }


    @Override
    public HashSet<String> convert(List<Resource> resourceVOs) {
        HashSet<String> module = new HashSet<String>();
        resourceVOs.forEach(resource -> {
            String url = resource.getUrl();
            if (StringUtils.isNotBlank(url)) {
                module.add(url.substring(0, url.indexOf("/")));
            }
        });
        return module;
    }
}
