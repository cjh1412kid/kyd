package com.nuite.manager.listener;

import com.nuite.manager.util.Const;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class WebAppContextListener implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Const.WEB_APP_CONTEXT = applicationContext;
        System.out.println("========获取Spring WebApplicationContext");
    }

}
