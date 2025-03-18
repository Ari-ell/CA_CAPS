package com.team1.sa56.caps.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.team1.sa56.caps.exception.Unauthorized;
import com.team1.sa56.caps.model.User;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws 
                                IOException, Unauthorized {
        
        String uri = request.getRequestURI();
        System.out.println("Intercepting " + uri);
        
        if(uri.startsWith("/css/") || uri.startsWith("/images/") || uri.startsWith("/caps/")){
            return true;
        }

        if(uri.equalsIgnoreCase("/") || uri.equalsIgnoreCase("/home")
            || uri.equalsIgnoreCase("/login")) {
                return true;
        }
            
        HttpSession session = request.getSession();
        User userSession = (User) session.getAttribute("user");
        if (userSession == null) {
            // No username, meaning not logged in yet
            // Redirect to login page
            response.sendRedirect("/login");
            return false;
        }
        
        String userRole = userSession.getRole();

        if(uri.startsWith("/admin") && !userRole.equals("Admin")){
            throw new Unauthorized("Please log in to continue");
        }
        
        if(uri.startsWith("/lecturer") && !userRole.equals("Lecturer")){
            throw new Unauthorized("Please log in to continue");
        }

        if(uri.startsWith("/student") && !userRole.equals("Student")){
            throw new Unauthorized("Please log in to continue");
        }
        
        return true;
    }
}
