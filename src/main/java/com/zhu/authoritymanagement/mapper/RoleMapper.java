package com.zhu.authoritymanagement.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.zhu.authoritymanagement.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhu.authoritymanagement.entity.RoleResource;
import com.zhu.authoritymanagement.vo.RoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@InterceptorIgnore(dataPermission = "true")
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 保存角色
     *
     * @param role          角色
     * @param roleResources 角色资源
     */
    void saveRole(@Param("role") Role role, @Param("roleResources") List<RoleResource> roleResources);

    /**
     * 更新角色
     *
     * @param role          角色
     * @param roleResources 角色资源
     */
    void updateRole(@Param("role") Role role, @Param("roleResources") List<RoleResource> roleResources);

    /**
     * 查询角色列表
     *
     * @return 角色列表
     */
    List<RoleVO> listRole();
}
