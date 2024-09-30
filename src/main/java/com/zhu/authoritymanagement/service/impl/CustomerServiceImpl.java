package com.zhu.authoritymanagement.service.impl;

import com.zhu.authoritymanagement.vo.CustomerVO;
import com.zhu.authoritymanagement.entity.Customer;
import com.zhu.authoritymanagement.mapper.CustomerMapper;
import com.zhu.authoritymanagement.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    private CustomerMapper customerMapper;

    @Autowired
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerVO> listCustomer() {
        return customerMapper.listCustomer();
    }
}
