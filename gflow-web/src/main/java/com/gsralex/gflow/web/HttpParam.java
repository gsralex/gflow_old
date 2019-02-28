package com.gsralex.gflow.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author gsralex
 * @version 2019/2/26
 */
public class HttpParam {

    private Map<String, String> paramMap = new HashMap<>();

    public HttpParam(String url) {
        int index = url.indexOf("?");
        if (index != -1) {
            String paramUrl = url.substring(index + 1);
            String[] paramArr = paramUrl.split("&");
            for (String param : paramArr) {
                String[] kv = param.split("=");
                String k = "", v = "";
                if (kv.length >= 1) {
                    k = kv[0];
                }
                if (kv.length >= 2) {
                    v = kv[1];
                }
                paramMap.put(k, v);
            }
        }
    }

    public Set<String> keySet() {
        return paramMap.keySet();
    }

    public String getString(String key) {
        return paramMap.get(key);
    }

}
