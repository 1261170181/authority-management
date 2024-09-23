package com.zhu.authoritymanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhu.authoritymanagement.vo.CustomerVO;
import com.zhu.authoritymanagement.entity.Customer;
import com.zhu.authoritymanagement.mapper.CustomerMapper;
import com.zhu.authoritymanagement.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    @Override
    public List<CustomerVO> listCustomer() {
        LambdaQueryWrapper<Customer> wrapper = Wrappers.<Customer>lambdaQuery()
                .select(Customer::getCustomerId, Customer::getName, Customer::getAge, Customer::getSex);
        List<Customer> customers = this.list(wrapper);

        return customers.stream().map(customer -> {
            CustomerVO customerVO = new CustomerVO();
            customerVO.setId(customer.getCustomerId());
            customerVO.setName(customer.getName());
            customerVO.setAge(customer.getAge());
            customerVO.setSex(customer.getSex());
            return customerVO;
        }).collect(Collectors.toList());
    }
}
