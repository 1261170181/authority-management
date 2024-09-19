package com.zhu.authoritymanagement.mp;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
/**
 *
 * 权限拦截
 *
 * @author Zhu
 * @since 2024-9-18
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI().substring(1);
        HttpSession session = request.getSession();
        HashSet<String> urls = (HashSet<String>) session.getAttribute("module");

        boolean result = urls.stream().anyMatch(url -> requestUrl.equals(url) || (requestUrl.startsWith(url) && requestUrl.length() > url.length()));

        if (!result) {
            response.sendRedirect("/error");
        }
        return result;
    }

}
