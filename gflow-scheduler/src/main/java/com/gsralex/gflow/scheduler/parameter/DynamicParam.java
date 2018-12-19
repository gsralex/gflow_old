package com.gsralex.gflow.scheduler.parameter;

/**
 * @author gsralex
 * @version 2018/12/6
 */
public interface DynamicParam {

    /**
     * 根据动态的传值替换掉key 比如:bizdate-1d
     * @param key
     * @return
     */
    String getValue(String key);

}
