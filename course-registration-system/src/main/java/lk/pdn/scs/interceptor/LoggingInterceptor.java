package lk.pdn.scs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoggingInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        
        String action = request.getRequestURI();
        if (action.equals("/login") && request.getMethod().equalsIgnoreCase("POST")) {
            System.out.println("Login attempt from: " + request.getParameter("email"));
        } else if (action.startsWith("/register/")) {
            System.out.println("Registration attempt for course: " + action.split("/")[2]);
        }
        
        return true;
    }
}