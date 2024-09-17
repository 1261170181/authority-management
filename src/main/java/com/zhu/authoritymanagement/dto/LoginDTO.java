package com.zhu.authoritymanagement.dto;

import com.zhu.authoritymanagement.entity.Account;
import lombok.Data;

@Data
public class LoginDTO {

    private String path;

    private String error;

    private Account account;

    private Long roleId;
}
