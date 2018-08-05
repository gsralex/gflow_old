package com.gsralex.gflow.core.context;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @version 2018/8/5
 */
public class Parameter {


    private Map<String, String> map;

    public Parameter(String str) {
        map = new HashMap<>();
        if (!StringUtils.isEmpty(str)) {
            String[] array = StringUtils.split(str, ",");
            for (String item : array) {
                String[] kv = StringUtils.split("=");
                String key = "", value = "";
                if (kv.length >= 1) {
                    key = kv[0];
                } else if (kv.length >= 2) {
                    value = kv[1];
                }
                map.put(key, value);
            }
        }
    }


    public String getString(String key) {
        return map.get(key);
    }

    public int getInt(String key, int defaultValue) {
        return NumberUtils.toInt(getString(key), defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return NumberUtils.toLong(getString(key), defaultValue);
    }
}
