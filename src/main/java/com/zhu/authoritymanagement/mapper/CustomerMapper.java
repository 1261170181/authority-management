package com.zhu.authoritymanagement.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.zhu.authoritymanagement.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@InterceptorIgnore(dataPermission = "true")
public interface CustomerMapper extends BaseMapper<Customer> {

}
