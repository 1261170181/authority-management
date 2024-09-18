package com.zhu.authoritymanagement.dto;

import lombok.Data;
/**
 *
 * 客户信息
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private Byte age;
    private String sex;
}
