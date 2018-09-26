package com.gsralex.gflow.executor.demo;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.executor.ExecuteProcess;

/**
 * @author gsralex
 * @version 2018/8/19
 */
public class DemoProcess3 implements ExecuteProcess {
    @Override
    public boolean process(long id, Parameter parameter) {
        System.out.println("DemoProcess3");
        return true;
    }
}
