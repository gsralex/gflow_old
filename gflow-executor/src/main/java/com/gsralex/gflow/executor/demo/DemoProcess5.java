package com.gsralex.gflow.executor.demo;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.executor.ExecuteProcess;
import com.gsralex.gflow.executor.JobDesc;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class DemoProcess5 implements ExecuteProcess {
    @Override
    public boolean process(JobDesc desc) {
        System.out.println("DemoProcess5");
        return true;
    }
}
