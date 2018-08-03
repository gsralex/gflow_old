package com.gsralex.gflow.scheduler.util;

import com.gsralex.gflow.scheduler.constant.GFlowException;
import org.apache.commons.lang3.math.NumberUtils;
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

    public static Date getTodayTime(String time) {
        String[] timeArray = time.split(":");
        String hh = "00", mm = "00", ss = "00";
        if (timeArray.length > 0) {
            hh = timeArray[0];
        }
        if (timeArray.length > 1) {
            mm = timeArray[1];
        }
        if (timeArray.length > 2) {
            ss = timeArray[2];
        }
        Date now = new Date();
        String date = DateFormatUtils.format(now, String.format("yyyy-MM-dd %s:%s:%s", hh, mm, ss));
        try {
            return DateUtils.parseDate(date, DF_FULLTIME);
        } catch (ParseException e) {
            throw new GFlowException();
        }
    }

    public static Date parseUnixTime(int unixTime){
        return new Date(unixTime*1000);
    }

    public static String formatBizDate(Date date) {
        return DateFormatUtils.format(date, DF_YYYYMMDD);
    }

    public static int getUnixTime() {
        return (int) (new Date().getTime() / 1000);
    }

    public static int getBizDate(){
        return Integer.parseInt(formatBizDate(new Date()));
    }
}
