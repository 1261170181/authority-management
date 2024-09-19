package com.zhu.authoritymanagement.controller;

import com.zhu.authoritymanagement.dto.CustomerDTO;
import com.zhu.authoritymanagement.entity.Customer;
import com.zhu.authoritymanagement.service.ICustomerService;
import com.zhu.authoritymanagement.util.Response;
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
    public Response<List<CustomerDTO>> listCustomer() {
        List<CustomerDTO> customerDTO = customerService.listCustomer();
        return Response.ok(customerDTO);
    }

    /**
     * 获取客户详情
     */
    @GetMapping("/detail")
    @ResponseBody
    public Response<List<Customer>> detailCustomer() {
        List<Customer> customers = customerService.list();
        return Response.ok(customers);
    }

    /**
     * 添加客户
     */
    @PostMapping("/add")
    @ResponseBody
    public Response<Object> addCustomer(@RequestBody Customer customer) {
        boolean success = customerService.save(customer);
        return Response.buildR(success);
    }

    /**
     * 更新客户信息
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public Response<Object> updateCustomer(@PathVariable Long id,@RequestBody Customer customer) {
        customer.setCustomerId(id);
        boolean success = customerService.updateById(customer);
        return Response.buildR(success);
    }

    /**
     * 删除客户
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Response<Object> deleteCustomer(@PathVariable Long id) {
        boolean success = customerService.removeById(id);
        return Response.buildR(success);
    }
}
