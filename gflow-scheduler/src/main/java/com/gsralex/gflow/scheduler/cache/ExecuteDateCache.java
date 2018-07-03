package com.gsralex.gflow.scheduler.cache;


import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public class ExecuteDateCache {

    private static Map<String, Boolean> executeMap = new HashMap<>();

    public void markExecute(long groupId, String date) {
        String key = getMarkExecuteKey(groupId, date);
        executeMap.put(key, true);
    }

    public boolean getExecute(long groupId, String date) {
        String key = getMarkExecuteKey(groupId, date);
        return executeMap.get(key);
    }

    private String getMarkExecuteKey(long groupId, String date) {
        return groupId + "_" + date;
    }
}
