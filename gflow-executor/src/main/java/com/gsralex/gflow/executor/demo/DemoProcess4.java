package com.gsralex.gflow.executor.demo;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.executor.ExecutorProcess;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class DemoProcess4 implements ExecutorProcess {

    @Override
    public boolean process(long id, Parameter parameter) {
        System.out.println("DemoProcess4");
        return true;
    }
}