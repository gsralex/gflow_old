package com.gsralex.gflow.executor.demo;

import com.gsralex.gflow.core.context.Parameter;
import com.gsralex.gflow.executor.AckExecuteProcess;
import com.gsralex.gflow.executor.ExecutorContext;

/**
 * @author gsralex
 * @version 2018/10/25
 */
public class DemoAckProcess1 implements AckExecuteProcess {
    @Override
    public void process(long id, Parameter parameter) {
        //TODO:业务代码
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExecutorContext.getContext().ack(id, true);
    }
}
