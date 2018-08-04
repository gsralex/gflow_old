package com.gsralex.gflow.executor.demo;

import com.gsralex.gflow.executor.ExecutorProcess;
import com.gsralex.gflow.executor.Parameter;

/**
 * @author gsralex
 * @version 2018/8/4
 */
public class DemoProcess implements ExecutorProcess {

    @Override
    public void process(int id, Parameter parameter) {
        System.out.println("Hello world");
    }
}
