package com.zhu.authoritymanagement.mp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhu
 * @since 2024-9-27
 */
public class DataPermissionContextHandler {
    private static final ThreadLocal<Map<String,Object>> CONTEXT = new ThreadLocal<>();

    public static Map<String,Object> getContext() {
        if(Objects.isNull(CONTEXT.get())) {
            synchronized (DataPermissionContextHandler.class) {
                if(Objects.isNull(CONTEXT.get())) {
                    Map dataContext = new HashMap(16);
                    CONTEXT.set(dataContext);
                }
            }
        }
        return CONTEXT.get();
    }

    public static void remove() {
        CONTEXT.remove();
    }
}
