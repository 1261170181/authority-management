package com.zhu.authoritymanagement.dto;

import com.zhu.authoritymanagement.entity.Account;
import lombok.Data;
/**
 *
 * 登录信息
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Data
public class LoginDTO {

    private String path;

    private String error;

    private Account account;

    private Long roleId;
}
