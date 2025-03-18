package com.team1.sa56.caps.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.team1.sa56.caps.interceptor.SecurityInterceptor;

@Component
public class WebAppConfig implements WebMvcConfigurer{
  
    @Autowired
    SecurityInterceptor securityInterceptor;
  
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor);
    }
}
