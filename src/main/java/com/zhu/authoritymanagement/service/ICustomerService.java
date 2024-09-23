package com.zhu.authoritymanagement.service;

import com.zhu.authoritymanagement.vo.CustomerVO;
import com.zhu.authoritymanagement.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
public interface ICustomerService extends IService<Customer> {

    /**
     * 获取客户列表
     *
     * @return 客户列表
     */
    List<CustomerVO> listCustomer();

}
