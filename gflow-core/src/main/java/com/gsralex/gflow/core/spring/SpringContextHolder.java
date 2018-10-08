package com.gsralex.gflow.core.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class SpringContextHolder {

    private static ApplicationContext context;

    public static void init(Class... annotatedClasses) {
        context = new AnnotationConfigApplicationContext(annotatedClasses);
    }

    public static void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }

    public static <T> T getBean(Class<T> type, Object... objects) {
        return context.getBean(type, objects);
    }
}
