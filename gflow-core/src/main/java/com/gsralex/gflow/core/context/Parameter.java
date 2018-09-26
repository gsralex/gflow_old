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

    public Parameter() {
        this("");
    }

    public Parameter(String str) {
        map = new HashMap<>();
        if (!StringUtils.isEmpty(str)) {
            String[] array = StringUtils.split(str, ",");
            for (String item : array) {
                String[] kv = StringUtils.split(item, "=");
                String key = "", value = "";
                if (kv.length >= 1) {
                    key = StringUtils.lowerCase(kv[0]);
                }
                if (kv.length >= 2) {
                    value = kv[1];
                }
                map.put(key, value);
            }
        }
    }

    public String getString(String key) {
        key = StringUtils.lowerCase(key);
        return map.get(key);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return NumberUtils.toInt(getString(key), defaultValue);
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        return NumberUtils.toLong(getString(key), defaultValue);
    }


    public void put(String key, String value) {
        map.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            b.append(String.format("%s=%s,", entry.getKey(), entry.getValue()));
        }
        return StringUtils.removeEnd(b.toString(), ",");
    }
}
