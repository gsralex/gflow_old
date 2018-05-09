package com.gsralex.gflow.scheduler.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/5/9
 */
public class BeanMap {

    private static Map<String, Object> beanMap = new HashMap<>();


    public <T> T getBean(Class<T> type) {
        return (T) beanMap.get(type.getName());
    }

    public void setBean(Class type, Object instance) {
        beanMap.put(type.getName(), instance);
    }

}
