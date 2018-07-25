namespace java com.gsralex.gflow.scheduler.thrift.gen




struct TJobResult{
    1:bool ok,
    2:string errmsg
}


struct TJobDesc{
    1:i64 id,
    2:i64 actionId,
    3:i64 jobGroupId,
    4:string parameter
}

struct TGroupJobDesc{
    1:i64 groupId,
    2:string parameter
}


service TScheduleService{
    TJobResult schedule(TJobDesc desc);
    TJobResult scheduleGroup(TGroupJobDesc desc);

}