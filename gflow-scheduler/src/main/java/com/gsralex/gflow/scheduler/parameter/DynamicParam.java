package com.gsralex.gflow.scheduler.parameter;

/**
 * @author gsralex
 * @version 2018/12/6
 */
public interface DynamicParam {

    /**
     * 获取正则表达式key
     * 比如 假设想捕获 date-3,date-1 这两个参数,就可以通过这个方法返回 date\-\d+，调度程序会通过正则匹配到这个DynamicParam
     * 再将date-3,w_1传递给getValue("date-3") getValue("date-1") getValue可以根据w_3 和 w_1返回具体的参数，比如20181203,20181205
     * @return
     */
    String getRegexKey();

    /**
     * 根据动态的传值替换掉key 比如:bizdate-1d
     * @param key
     * @return
     */
    String getValue(String key);

}
