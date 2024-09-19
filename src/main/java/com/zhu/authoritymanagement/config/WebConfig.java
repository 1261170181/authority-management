package com.zhu.authoritymanagement.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zhu.authoritymanagement.mp.MyInterceptor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;
/**
 *
 * WebMvc配置类
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 日期格式化:年月日
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 日期格式化:年月日时分秒,HH是24小时制
     */
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 全局配置
     * 如果要处理Java8的日期类型,需要在Spring Boot的SpringMvc配置类中定义Jackson的日期转换格式
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(DATE_TIME_FORMAT);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        };
    }

    /**
     * 拦截器的配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/auth/login", "/auth/logout", "/error");
    }
}
