package com.zhu.authoritymanagement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("customer")
public class Customer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    @TableId(value = "customer_id", type = IdType.AUTO)
    private Long customerId;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 年龄
     */
    @TableField("age")
    private Byte age;
}
