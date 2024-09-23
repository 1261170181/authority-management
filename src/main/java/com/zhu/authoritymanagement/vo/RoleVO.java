package com.zhu.authoritymanagement.vo;

import lombok.Data;

import java.util.List;

/**
 * 角色VO
 *
 * @author zhu
 * @since 2024-09-23
 */
@Data
public class RoleVO {

    private Long roleId;

    private String roleName;

    private String remark;

    private List<Long> resourceIds;
}
