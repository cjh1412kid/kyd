package com.nuite.manager.interceptor;

import com.nuite.manager.entity.User;
import com.nuite.manager.util.Const;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        String path = request.getServletPath();
        if (path.matches(Const.NO_INTERCEPTOR_PATH)) {
            return true;
        } else if (path.contains(Const.INTEGERFACE_PATH)) {
            return true;
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Const.SESSION_USER);
            if (user != null) {
                return true;
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
    }

}
