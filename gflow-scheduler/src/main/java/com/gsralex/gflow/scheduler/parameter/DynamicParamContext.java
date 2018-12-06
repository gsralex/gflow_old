package com.gsralex.gflow.scheduler.parameter;

import com.gsralex.gflow.core.context.Parameter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gsralex
 * @version 2018/12/6
 */
public class DynamicParamContext {

    private static final String TAG_DYNAMIC = "$";

    private static DynamicParamContext currentContext = new DynamicParamContext();


    private Map<DynamicParam, Pattern> patterns = new HashMap<>();

    private DynamicParamContext() {
    }

    public void addParam(DynamicParam param) {
        patterns.put(param, Pattern.compile(param.getRegexKey()));
    }

    public void parser(Parameter parameter) {
        Set<String> keySet = parameter.listKeys();
        for (Map.Entry<DynamicParam, Pattern> entry : patterns.entrySet()) {
            DynamicParam param = entry.getKey();
            Pattern pattern = entry.getValue();
            for (String key : keySet) {
                if (StringUtils.startsWith(key, TAG_DYNAMIC)) {
                    Matcher matcher = pattern.matcher(parameter.getString(key));
                    if (matcher.find()) {
                        String matchValue = matcher.group();
                        parameter.remove(key);
                        String newKey = StringUtils.removeStart(key, TAG_DYNAMIC);
                        parameter.put(newKey, param.getValue(matchValue));
                    }
                }
            }
        }
    }

    public static DynamicParamContext getContext() {
        return currentContext;
    }
}
