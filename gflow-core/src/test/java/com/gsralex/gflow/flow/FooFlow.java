package com.gsralex.gflow.flow;

/**
 * @author gsralex
 * @date 2018/2/12
 */
public class FooFlow implements Flow {
    public FlowResult execute(FlowParameter parameter) {
        FlowResult r = new FlowResult();

        //提交spark任务
        //提交hive任务
        //提交aliyun odps任务

        return r;
    }
}
