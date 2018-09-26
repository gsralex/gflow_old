package com.gsralex.gflow.executor.demo;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.executor.ExecuteProcess;

/**
 * @author gsralex
 * @version 2018/8/8
 */
public class DemoProcess2 implements ExecuteProcess {
    @Override
    public boolean process(long id, Parameter parameter) {
        System.out.println("DemoProcess2");
        return true;
    }
}
