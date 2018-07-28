package com.gsralex.gflow.scheduler;

import com.gsralex.gflow.scheduler.context.GFlowContext;
import com.gsralex.gflow.scheduler.thrift.ThriftSchedulerServer;

public class MainJob {

    public static void main(String[] args){
        MainJob job=new MainJob();
        job.init();
    }

    private void init(){
        GFlowContext context=new GFlowContext();
        context.init();

        SchedulerServer server=new ThriftSchedulerServer();
        server.start(context.getConfig().getPort());
    }
}
