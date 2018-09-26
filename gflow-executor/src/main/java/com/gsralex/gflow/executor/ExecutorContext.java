package com.gsralex.gflow.executor;

import com.gsralex.gflow.executor.spring.SpringContextHolder;
import org.springframework.context.ApplicationContext;

/**
 * @author gsralex
 * @version 2018/9/21
 */
public class ExecutorContext {

    private static ExecutorContext currentContext = new ExecutorContext();

    private ExecutorContext() {
    }

    public static ExecutorContext getContext() {
        return currentContext;
    }

    private SpringContextHolder contextHolder = new SpringContextHolder();

    public <T> T getSpringBean(Class<T> type) {
        return contextHolder.getBean(type);
    }

    public boolean containsBean(Class type) {
        return contextHolder.containsBean(type);
    }

    public void setSpringApplicationContext(ApplicationContext springContext) {
        contextHolder.setApplicationContext(springContext);
    }
}
