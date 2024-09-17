package com.zhu.authoritymanagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhu.authoritymanagement.dto.CustomerDTO;
import com.zhu.authoritymanagement.entity.Customer;
import com.zhu.authoritymanagement.service.ICustomerService;
import com.zhu.authoritymanagement.util.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/list")
    @ResponseBody
    public Response<List<CustomerDTO>> listCustomer() {
        LambdaQueryWrapper<Customer> wrapper = Wrappers.<Customer>lambdaQuery()
                .select(Customer::getCustomerId, Customer::getName, Customer::getAge, Customer::getSex);
        List<Customer> customers = customerService.list(wrapper);

        List<CustomerDTO> customerDTOs = customers.stream().map(customer -> {
            CustomerDTO dto = new CustomerDTO();
            dto.setId(customer.getCustomerId());
            dto.setName(customer.getName());
            dto.setAge(customer.getAge());
            dto.setSex(customer.getSex());
            return dto;
        }).collect(Collectors.toList());

        return Response.ok(customerDTOs);
    }

    @GetMapping("/detail")
    @ResponseBody
    public Response<List<Customer>> detailCustomer() {
        List<Customer> customers = customerService.list();
        return Response.ok(customers);
    }

    @PostMapping("/add")
    @ResponseBody
    public Response<Object> addCustomer(@RequestBody Customer customer) {
        boolean success = customerService.save(customer);
        return Response.buildR(success);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public Response<Object> updateCustomer(@PathVariable Long id,@RequestBody Customer customer) {
        customer.setCustomerId(id);
        boolean success = customerService.updateById(customer);
        return Response.buildR(success);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Response<Object> deleteCustomer(@PathVariable Long id) {
        boolean success = customerService.removeById(id);
        return Response.buildR(success);
    }



}
