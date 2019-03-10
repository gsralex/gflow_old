namespace java com.gsralex.gflow.pub.thriftgen.scheduler


include "gflow.thrift"

//任务组描述
struct TGroupJobReq{
    1:i64 groupId
    2:string parameter
    3:string accessToken
}

struct TScheduleGroupResp{
    1:i64 jobGroupId
    2:i32 code
    3:string msg
}


//任务描述
struct TJobReq{
    1:i64 id
    2:i64 actionId
    3:i64 jobGroupId
    4:string className
    5:string parameter
    6:i32 index
    7:i64 retryJobId //重试任务Id，如果不是重试任务，此值为0
    8:string accessToken
}

struct TJobResp{
    1:i64 jobId
    2:i32 code
    3:string msg
}

struct TAckReq{
    1:i64 jobId
    2:i32 code
    3:string msg
    4:string accessToken
}


enum GroupStatus{
    EXECUTING=1
    PAUSE=2
    STOP=3
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
    1:i64 id
    2:JobStatus status
}

struct TGetJobReq{
    1:i64 id
    2:string accessToken
}

struct TGetJobResp{
    1:i32 code
    2:TJob job
    3:string msg
}


struct TExecutorHbReq{
    1:string ip
    2:i32 port
    3:string tag
    4:bool online
    5:string accessToken;
}

struct TScheduleHbReq{
    1:string ip
    2:i32 port
    3:string online
    4:string accessToken
}

struct TNode{
    1:string ip
    2:i32 port
}

struct TNodeResp{
    1:i32 code
    2:string msg
    3:list<TNode> nodeList
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
    //executor 心跳
    gflow.TResp executorHb(1:TExecutorHbReq req);
    //scheduler 心跳
    gflow.TResp schedulerHb(1:TScheduleHbReq req);
    //更新executor节点
    gflow.TResp updateExecutorNode(1:TExecutorHbReq req);
    //获取所有的调度节点
    TNodeResp listSchedulerNode(1:gflow.TReq req);

    string serverStatus();




//    //获取任务组
//    TGetJobGroupResp getGroup(1:TGetJobGroupReq req);
//    //获取action
//    TGetJobResp getJob(1:TGetJobReq req);
}


service TExecutorService{
    gflow.TResp schedule(1:TJobReq desc);
    gflow.TResp updateSchedulerNode(1:TScheduleHbReq req);
}
