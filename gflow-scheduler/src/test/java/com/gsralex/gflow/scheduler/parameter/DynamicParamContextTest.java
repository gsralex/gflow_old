package com.gsralex.gflow.scheduler.parameter;

import com.gsralex.gflow.core.context.Parameter;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * @author gsralex
 * @version 2018/12/6
 */
public class DynamicParamContextTest {
    @Test
    public void parser() throws Exception {
        DynamicParamContext context = DynamicParamContext.getContext();
        DynamicParam param1 = new DynamicParam() {
            @Override
            public String getRegexKey() {
                return "bizdate\\-\\d+d";
            }

            @Override
            public String getValue(String key) {
                //bizdate-1d
                Pattern pattern = Pattern.compile("bizdate-(?<day>\\d+)d");
                Matcher matcher = pattern.matcher(key);
                matcher.find();
                int day = Integer.parseInt(matcher.group("day"));
                return DateFormatUtils.format(DateUtils.addDays(new Date(), day), "yyyyMMdd");
            }
        };
        context.addParam(param1);
        Parameter parameter = new Parameter("$bizdate=bizdate-3d");
        context.parser(parameter);
        String date = DateFormatUtils.format(DateUtils.addDays(new Date(), 3), "yyyyMMdd");
        Assert.assertEquals(parameter.getString("bizdate"), date);

    }

}