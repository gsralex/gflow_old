package com.gsralex.gflow.scheduler.sql;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class IdUtils {


    public static String longToInts(List<Long> list){
        StringBuilder b=new StringBuilder();
        for(Long id:list){
            b.append(id).append(",");
        }
        return StringUtils.removeEnd(b.toString(),",");
    }

}
