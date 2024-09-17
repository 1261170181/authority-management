package com.zhu.authoritymanagement.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zhu.authoritymanagement.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    List<Resource> listResourceByRoleId(@Param(Constants.WRAPPER)Wrapper<Resource> wrapper, @Param("roleId") Long roleId);

}
