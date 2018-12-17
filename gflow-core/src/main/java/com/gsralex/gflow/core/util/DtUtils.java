package com.gsralex.gflow.core.util;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author gsralex
 * @version 2018/6/2
 */
public class DtUtils {

    private static final String DF_FULLTIME = "yyyy-MM-dd HH:mm:ss";
    private static final String DF_YYYYMMDD = "yyyyMMdd";


    public static Date parseUnixTime(int unixTime) {
        return new Date(unixTime * 1000);
    }

    public static Date parseUnixTimeMs(long unixTime) {
        return new Date(unixTime);
    }

    public static String formatBizDate(Date date) {
        return DateFormatUtils.format(date, DF_YYYYMMDD);
    }

    public static int getBizDate() {
        return Integer.parseInt(formatBizDate(new Date()));
    }
}
