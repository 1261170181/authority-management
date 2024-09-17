package com.zhu.authoritymanagement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author zhu
 * @since 2024-09-12
 */
@Data
@TableName("account_role")
public class AccountRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "account_role_id", type = IdType.AUTO)
    private Long accountRoleId;

    /**
     * 账号id
     */
    @TableField("account_id")
    private Long accountId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;
}
