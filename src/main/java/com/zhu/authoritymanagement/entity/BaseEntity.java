package com.zhu.authoritymanagement.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "modified_time", fill = FieldFill.UPDATE)
    private LocalDateTime modifiedTime;

    /**
     * 创建人
     */
    @TableField(value = "create_account", fill = FieldFill.INSERT)
    private String createAccount;

    /**
     * 修改人
     */
    @TableField(value = "modified_account", fill = FieldFill.UPDATE)
    private String modifiedAccount;

    /**
     * 删除标识
     */
    @TableLogic
    @TableField("deleted")
    private Byte deleted;
}
