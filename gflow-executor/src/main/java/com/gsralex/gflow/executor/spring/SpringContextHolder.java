package com.gsralex.gflow.executor.spring;

import org.springframework.context.ApplicationContext;

public class SpringContextHolder {

    private static ApplicationContext context;

    public static void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }

    public static <T> T getBean(Class<T> type, Object... objects) {
        return context.getBean(type, objects);
    }
}
