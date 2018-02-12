package com.gsralex.gflow.flow;

import org.apache.commons.lang3.math.NumberUtils;


import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @date 2018/2/12
 */
public class FlowParameter {

    private String parameter;
    private Map<String, String> pMap;

    public FlowParameter(String parameter) {
        this.parameter = parameter;
        init();
    }

    private void init() {
        pMap = new HashMap<>();
        String[] pArray = this.parameter.split(",");
        for (String p : pArray) {
            String[] pValue = p.split("=");
            String key = "", value = "";
            if (pValue.length >= 1) {
                key = pValue[0];
            }
            if (pValue.length >= 2) {
                value = pValue[1];
            }
            pMap.put(key, value);
        }
    }

    public Map<String, String> getParameterMap() {
        return pMap;
    }

    public String getString(String key) {
        return pMap.get(key);
    }

    public short getShort(String key) {
        short defaultValue = 0;
        return getShort(key, defaultValue);
    }

    public short getShort(String key, short defaultValue) {
        return NumberUtils.toShort(key, defaultValue);
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

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, double defaultValue) {
        return NumberUtils.toDouble(getString(key), defaultValue);
    }

    public float getFloat(String key) {
        return getFloat(key, 0F);
    }

    public float getFloat(String key, Float defaultValue) {
        return NumberUtils.toFloat(key, defaultValue);
    }
}
