package com.gsralex.gflow.executor.demo;

import com.gsralex.gflow.core.api.Flow;
import com.gsralex.gflow.core.api.FlowParameter;
import com.gsralex.gflow.core.api.FlowResult;

/**
 * @author gsralex
 * @version 2018/3/18
 */
public class DemoService implements Flow {


    @Override
    public FlowResult handle(FlowParameter parameter) {
        String bizDate = parameter.getString("bizdate");
        System.out.println("bizdate:" + bizDate);
        return new FlowResult(true, "");
    }
}
