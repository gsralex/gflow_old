package com.gsralex.gflow.executor.spring;

import org.springframework.context.ApplicationContext;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class SpringContextHolder {

    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }


    public <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

    public <T> boolean containsBean(Class<T> type) {
        return context.containsBean(type.getName());
    }
}
