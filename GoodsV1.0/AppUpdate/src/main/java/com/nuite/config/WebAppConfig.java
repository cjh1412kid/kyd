package com.nuite.config;

import com.nuite.manager.interceptor.LoginHandlerInterceptor;
import com.nuite.manager.interceptor.RightsHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    private final RightsHandlerInterceptor rightsHandlerInterceptor;

    private final LoginHandlerInterceptor loginHandlerInterceptor;

    @Autowired
    public WebAppConfig(RightsHandlerInterceptor rightsHandlerInterceptor, LoginHandlerInterceptor loginHandlerInterceptor) {
        this.rightsHandlerInterceptor = rightsHandlerInterceptor;
        this.loginHandlerInterceptor = loginHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rightsHandlerInterceptor);
        registry.addInterceptor(loginHandlerInterceptor);
    }
}
