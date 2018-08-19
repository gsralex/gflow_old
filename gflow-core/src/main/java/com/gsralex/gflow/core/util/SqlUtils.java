package com.gsralex.gflow.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author gsralex
 * @version 2018/8/14
 */
public class SqlUtils {

    public static String idToInt(int[] ids) {
        StringBuilder builder = new StringBuilder();
        for (int id : ids) {
            builder.append(id).append(",");
        }
        return StringUtils.removeEnd(builder.toString(), ",");
    }
}
