package com.gsralex.gflow.core.context;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        if (str != null) {
            String[] array = str.split(",");
            for (String item : array) {
                String[] kv = item.split("=");
                String key = "", value = "";
                if (kv.length >= 1) {
                    key = kv[0].toLowerCase();
                }
                if (kv.length >= 2) {
                    value = kv[1];
                }
                map.put(key, value);
            }
        }
    }

    public String getString(String key) {
        key = key.toLowerCase();
        return map.get(key);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(getString(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        try {
            return Long.parseLong(getString(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, double defaultValue) {
        try {
            return Double.parseDouble(getString(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    public float getFloat(String key, float defaultValue) {
        try {
            return Float.parseFloat(getString(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public void remove(String key) {
        map.remove(key);
    }

    public Set<String> listKeys() {
        return map.keySet();
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            b.append(String.format("%s=%s,", entry.getKey(), entry.getValue()));
        }
        return b.deleteCharAt(b.length() - 1).toString();
    }
}
