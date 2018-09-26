package com.gsralex.gflow.executor.demo;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.executor.ExecuteProcess;
import org.springframework.stereotype.Service;

/**
 * @author gsralex
 * @version 2018/8/8
 */
@Service
public class DemoProcess1 implements ExecuteProcess {
    @Override
    public boolean process(long id, Parameter parameter) {
        System.out.println("DemoProcess1");
        return true;
    }
}
