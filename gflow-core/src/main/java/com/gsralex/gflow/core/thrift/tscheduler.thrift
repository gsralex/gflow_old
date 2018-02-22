enum TJobStatus{
    OK=0,
    ERROR=2
}


struct TJobResult{
    1:TJobStatus status,
    2:string errmsg
}



struct TJobDesc{
    1:i64 id,
    2:string parameter
}

struct TJobGroupDesc{
    1:i64 id,
    2:string parameter
}

service TScheduleService{
    TJobResult schedule(TJobDesc desc);
    TJobResult scheduleGroup(TJobGroupDesc desc);
}


service TReplyServie{
    TJobResult reply(TJobDesc desc,TJobResult result);
}








