namespace java com.gsralex.gflow.core.thrift.gen



//调度结果
struct TResult{
    1:i32 code,
    2:string msg
}

//任务描述
struct TJobDesc{
    1:i64 id,
    2:i64 actionId,
    3:i64 jobGroupId,
    4:string className,
    5:string parameter,
    6:i32 index,
    7:i64 retryJobId, //重试任务Id，如果不是重试任务，此值为0
    8:string accessToken

}

//任务组描述
struct TGroupJobDesc{
    1:i64 groupId,
    2:string parameter
    3:string accessToken
}

struct TAckDesc{
    1:i64 jobId,
    2:i32 code,
    3:string errmsg,
    4:string accessToken
}

//任务结果
struct TJobResult{
    1:TJobDesc jobDesc
    2:i32 code
}



service TScheduleService{
    TResult schedule(1:TJobDesc desc);
    TResult scheduleGroup(1:TGroupJobDesc desc);
    TResult pauseGroup(1:i64 id);
    TResult stopGroup(1:i64 id);
    TResult setGflowSettings(1:string key,2:string value);
}

service TScheduleAckService{
    TResult ack(1:TAckDesc desc);
}

service TExecutorService{
    TResult schedule(1:TJobDesc desc);
    TResult heartbeat();
}

