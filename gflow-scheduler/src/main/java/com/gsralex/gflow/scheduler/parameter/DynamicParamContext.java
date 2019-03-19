package com.gsralex.gflow.scheduler.parameter;

import com.gsralex.gflow.core.context.Parameter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gsralex
 * @version 2018/12/6
 */
public class DynamicParamContext {

    private static final String TAG_DYNAMIC = "\\$\\{(?<value>.*)\\}";

    private static DynamicParamContext currentContext = new DynamicParamContext();

    private static final Pattern PATTERN = Pattern.compile(TAG_DYNAMIC);

    private List<DynamicParam> paramList = new ArrayList<>();

    private DynamicParamContext() {
    }

    public void addParam(DynamicParam param) {
        paramList.add(param);
    }

    public void parser(Parameter parameter) {
        Set<String> keySet = parameter.listKeys();
        for (DynamicParam param : paramList) {
            for (String key : keySet) {
                String value = parameter.getString(key);
                Matcher dynamicMatcher = PATTERN.matcher(value);
                if (dynamicMatcher.find()) {
                    String matchValue = dynamicMatcher.group("value");
                    String newValue = param.getValue(matchValue);
                    parameter.put(key, newValue);
                }
            }
        }
    }

    public static DynamicParamContext getContext() {
        return currentContext;
    }
}
