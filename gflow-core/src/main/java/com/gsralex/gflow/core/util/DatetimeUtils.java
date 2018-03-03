package com.gsralex.gflow.core.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author gsralex
 * @date 2018/3/3
 */
public class DatetimeUtils {

    private static final String YYYYMMDD = "yyyyMMdd";
    private static final String YYYYMMDD_HHMMSS = "yyyyMMdd HH:mm:ss";


    public static boolean after(String time) throws ParseException {
        return after(new Date(), time);
    }

    /**
     * 比较设定时间是否在指定日期后面
     *
     * @param now  指定日期
     * @param time 时间HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static boolean after(Date now, String time) {
        //HH:mm:ss
        String day = DateFormatUtils.format(now, YYYYMMDD);
        String date = String.format("%s %s", day, time);
        try {
            Date setDate = DateUtils.parseDate(date, YYYYMMDD_HHMMSS);
            return setDate.after(now);
        } catch (ParseException e) {
            return false;
        }
    }


    public static boolean dayEqualWeek(int amount) {
        return dayEqualWeek(new Date(), amount);
    }

    public static boolean dayEqualWeek(Date now, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        return calendar.get(Calendar.DAY_OF_WEEK) == amount ? true : false;
    }

    public static boolean dayEqualMonth(int amount) {
        return dayEqualMonth(new Date(), amount);
    }

    public static boolean dayEqualMonth(Date now, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        return calendar.get(Calendar.DAY_OF_MONTH) == amount ? true : false;
    }

    public static int getDs() {
        String ds = DateFormatUtils.format(new Date(), YYYYMMDD);
        return NumberUtils.toInt(ds);
    }
}
