package com.zhu.authoritymanagement.mp;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

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
        HashSet<String> urls = (HashSet<String>) request.getSession().getAttribute("module");
        if (urls == null) {
            return true;
        }
        boolean result = urls.stream().anyMatch(subString::equals);
        if (!result) {
            response.sendRedirect("/");
        }
        return result;
    }

}
