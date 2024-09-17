package com.zhu.authoritymanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhu.authoritymanagement.mapper")
public class AuthorityManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityManagementApplication.class, args);
    }

}
