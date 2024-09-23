package com.zhu.authoritymanagement.aop;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author zhu
 * @since 2024-9-23
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ControllerWebLog {
    String name();//所调用接口的名称
}