package com.zhu.authoritymanagement.exception;

import com.zhu.authoritymanagement.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author zhu
 * @since 2024-9-18
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Response<Object> handleException(Exception e) {
        logger.error("服务器内部错误: ", e);
        return Response.failed(e.getMessage());
    }

    @ExceptionHandler
    public Object handler(NullPointerException e) {
        return Response.failed("发⽣NullPointerException:"+e.getMessage());
    }

    @ExceptionHandler
    public Object handler(ArithmeticException e) {
        return Response.failed("发⽣ArithmeticException:"+e.getMessage());
    }
}
