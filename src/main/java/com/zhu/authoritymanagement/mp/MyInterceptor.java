package com.zhu.authoritymanagement.mp;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

/**
 * 权限拦截
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();
        String subString = requestUrl.substring(1);
        int index = subString.indexOf("/");
        if (index != -1) {
            subString = subString.substring(0, index);
        }
        @SuppressWarnings("unchecked")
        HashSet<String> urls = (HashSet<String>) request.getSession().getAttribute("module");
        boolean result = urls != null && urls.stream().anyMatch(subString::equals);
        if (!result) {
            response.sendError(403, "没有权限");
        }
        return result;
    }

}
