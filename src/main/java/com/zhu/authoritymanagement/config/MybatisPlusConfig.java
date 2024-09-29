package com.zhu.authoritymanagement.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zhu.authoritymanagement.mp.CustomDataPermissionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MP配置类
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor();
        dataPermissionInterceptor.setDataPermissionHandler(new CustomDataPermissionHandler());
        interceptor.addInnerInterceptor(dataPermissionInterceptor);

        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public MySqlInjector sqlInjector() {
        return new MySqlInjector();
    }
}