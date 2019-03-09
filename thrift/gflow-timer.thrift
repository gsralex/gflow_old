namespace java com.gsralex.gflow.pub.thriftgen.timer

include "gflow.thrift"

struct TTimeReq{
    1:i64 id,
    2:i64 flowGroupId,
    3:string time
    4:bool active
    5:string accessToken
}


service TTimerService{
    gflow.TResp saveTimer(1:TTimeReq req);
    gflow.TResp updateTimer(1:TTimeReq req);
    gflow.TResp removeTimer(1:gflow.TIdReq req);
}