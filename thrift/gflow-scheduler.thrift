namespace java com.gsralex.gflow.core.thriftgen.scheduler


include "gflow.thrift"

//任务组描述
struct TGroupJobReq{
    1:i64 groupId,
    2:string parameter
    3:string accessToken
}

struct TScheduleGroupResp{
    1:i64 jobGroupId;
    2:i32 code,
    3:string msg
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

struct TJobResp{
    1:i64 jobId,
    2:i32 code
    3:string msg
}

struct TAckReq{
    1:i64 jobId,
    2:i32 code,
    3:string msg,
    4:string accessToken
}


enum GroupStatus{
    EXECUTING=1,
    PAUSE=2,
    STOP=3,
    FINISH=4
}


struct TGetJobGroupReq{
    1:i64 id
    2:string accessToken
}

struct TJobGroup{
    1:i64 id
    2:GroupStatus status
}

struct TGetJobGroupResp{
    1:i32 code
    2:TJobGroup jobGroup;
}

enum JobStatus{
    SENDOK=1
    SENDERR=2
    EXECUTEOK=3
    EXECUTEERR=4
}

struct TJob{
    1:i64 id,
    2:JobStatus status
}

struct TGetJobReq{
    1:i64 id,
    2:string accessToken
}

struct TGetJobResp{
    1:i32 code
    2:TJob job,
    3:string msg
}


service TScheduleService{
    //调度action
    TJobResp scheduleAction(1:TJobReq req);
    //调度任务组
    TScheduleGroupResp scheduleGroup(1:TGroupJobReq req);
    //暂定任务组
    gflow.TResp pauseGroup(1:TGroupJobReq req);
    //停止任务组
    gflow.TResp stopGroup(1:TGroupJobReq req);
    //任务应答
    gflow.TResp ack(1:TAckReq req);
    //同步任务
    gflow.TResp ackMaster(1:TAckReq req);
    //心跳
    gflow.TResp executerHeartBeat();
//    //获取任务组
//    TGetJobGroupResp getGroup(1:TGetJobGroupReq req);
//    //获取action
//    TGetJobResp getJob(1:TGetJobReq req);
}


service TExecutorService{
    gflow.TResp schedule(1:TJobReq desc);
}
