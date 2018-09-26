package com.gsralex.gflow.scheduler.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class SpringContextUtils {

    private static ApplicationContext context;

    public static void init() {
        context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    }

    public static <T> T getBean(Class<T> type, Object... objects) {
        return context.getBean(type, objects);
    }
}
