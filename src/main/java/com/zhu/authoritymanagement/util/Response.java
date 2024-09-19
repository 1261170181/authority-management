package com.zhu.authoritymanagement.util;

import lombok.Data;

/**
 *
 * 通用返回类
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Data
public class Response<T> {
    private int status;
    private String message;
    private T data;

    public Response(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(200, "操作成功", data);
    }

    public static <T> Response<T> failed(String message) {
        return new Response<>(500, message, null);
    }

    public static Response<Object> buildR(boolean success) {
        if (success) {
            return ok(null);
        }
        return failed("操作失败");
    }
}