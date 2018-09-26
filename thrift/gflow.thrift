namespace java com.gsralex.gflow.core.thrift.gen



//调度结果
struct TResult{
    1:bool ok,
    2:string errmsg
}

//任务描述
struct TJobDesc{
    1:i64 id,
    2:i64 actionId,
    3:i64 jobGroupId,
    4:string className,
    5:string parameter,
    6:i32 index
    7:i64 retryJobId //重试任务Id，如果不是重试任务，此值为0

}

//任务组描述
struct TGroupJobDesc{
    1:i64 groupId,
    2:string parameter
}

//任务结果
struct TJobResult{
    1:TJobDesc jobDesc
    2:bool ok
}



service TScheduleService{
    TResult schedule(1:TJobDesc desc);
    TResult scheduleGroup(1:TGroupJobDesc desc);
}

service TScheduleAckService{
    TResult ack(1:i64 jobId,2:bool ok)
}

service TExecutorService{
    TResult schedule(1:TJobDesc desc);
    TResult heartbeat();
}

