package com.zhu.authoritymanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * 启动类
 *
 * @author Zhu
 * @since 2024-9-18
 */
@SpringBootApplication
@MapperScan("com.zhu.authoritymanagement.mapper")
public class AuthorityManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityManagementApplication.class, args);
    }

}
