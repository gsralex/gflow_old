package com.gsralex.gflow.core.util;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * @author gsralex
 * @date 2018/3/3
 */
public class DatetimeUtilsTest {

    final String TIMEFORMAT = "yyyyMMdd HH:mm:ss";

    @Test
    public void after() throws Exception {
        String time = "20180303 16:00:00";
        Date date = DateUtils.parseDate(time, TIMEFORMAT);
        Assert.assertEquals(true, DatetimeUtils.after(date, "16:05:00"));
        Assert.assertEquals(false, DatetimeUtils.after(date, "15:00:00"));

    }

    @Test
    public void dayEqualWeek() throws Exception {
        Date date = DateUtils.parseDate("20180303 16:00:00", TIMEFORMAT);
        Date date1 = DateUtils.parseDate("20180301 16:00:00", TIMEFORMAT);
        Date date2 = DateUtils.parseDate("20180304 16:00:00", TIMEFORMAT);

        Assert.assertEquals(true, DatetimeUtils.dayEqualWeek(date, 7));
        Assert.assertEquals(true, DatetimeUtils.dayEqualWeek(date1, 5));
        Assert.assertEquals(true, DatetimeUtils.dayEqualWeek(date2, 1));
    }

    @Test
    public void dayEqualMonth() throws Exception {
        String time = "20180303 16:00:00";
        Date date = DateUtils.parseDate(time, TIMEFORMAT);
        Assert.assertEquals(true, DatetimeUtils.dayEqualMonth(date, 3));
    }
}