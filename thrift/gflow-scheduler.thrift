namespace java com.gsralex.gflow.core.thriftgen.scheduler


include "gflow.thrift"

//任务组描述
struct TGroupJobReq{
    1:i64 groupId,
    2:string parameter
    3:string accessToken
}

//任务描述
struct TJobReq{
    1:i64 id,
    2:i64 actionId,
    3:i64 jobGroupId,
    4:string className,
    5:string parameter,
    6:i32 index,
    7:i64 retryJobId, //重试任务Id，如果不是重试任务，此值为0
    8:string accessToken
}

struct TAckReq{
    1:i64 jobId,
    2:i32 code,
    3:string errmsg,
    4:string accessToken
}


service TScheduleService{
    gflow.TResp scheduleAction(1:TJobReq req);
    gflow.TResp scheduleGroup(1:TGroupJobReq req);
    gflow.TResp pauseGroup(1:TGroupJobReq req);
    gflow.TResp stopGroup(1:TGroupJobReq req);
    gflow.TResp ack(1:TAckReq req);
}


service TExecutorService{
    gflow.TResp schedule(1:TJobReq desc);
    gflow.TResp heartbeat();
}
