package com.zhu.authoritymanagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhu.authoritymanagement.entity.Customer;
import com.zhu.authoritymanagement.service.ICustomerService;
import com.zhu.authoritymanagement.util.Response;
import com.zhu.authoritymanagement.vo.CustomerVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 客户表 前端控制器
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private ICustomerService customerService;

    /**
     * 获取客户列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Response<List<CustomerVO>> listCustomer() {
        return Response.ok(customerService.listCustomer());
    }

    /**
     * 获取客户详情
     */
    @GetMapping("/detail")
    @ResponseBody
    public Response<List<Customer>> detailCustomer() {
        return Response.ok(customerService.list());
    }

    /**
     * 添加客户
     */
    @PostMapping("/add")
    @ResponseBody
    public Response<Object> addCustomer(@RequestBody Customer customer) {
        LambdaQueryWrapper<Customer> queryWrapper = Wrappers.<Customer>lambdaQuery()
                .eq(Customer::getName, customer.getName())
                .eq(Customer::getAge, customer.getAge())
                .eq(Customer::getSex, customer.getSex());
        if (customerService.count(queryWrapper) > 0) {
            return Response.failed("客户已在数据库中");
        }
        return Response.buildResult(customerService.save(customer));
    }

    /**
     * 更新客户信息
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public Response<Object> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        if (customerService.getById(id) == null) {
            return Response.failed("客户不存在");
        }
        customer.setCustomerId(id);
        return Response.buildResult(customerService.updateById(customer));
    }

    /**
     * 删除客户
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Response<Object> deleteCustomer(@PathVariable Long id) {
        if (customerService.getById(id) == null) {
            return Response.failed("客户不存在");
        }
        return Response.buildResult(customerService.removeById(id));
    }
}
