package io.nuite.modules.online_sales_app.config;

import io.nuite.modules.online_sales_app.interceptor.XcxAuthorizationInterceptor;
import io.nuite.modules.online_sales_app.resolver.XcxCustomHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class XcxMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private XcxAuthorizationInterceptor authorizationInterceptor;
    @Autowired
    private XcxCustomHandlerMethodArgumentResolver customHandlerMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //如果controller路由被shiroConfig.java过滤，但其中有一些接口需要使用@Login注解判断登录，需要在此/app/**后继续添加路径
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/online/miniapp/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(customHandlerMethodArgumentResolver);
    }
}
