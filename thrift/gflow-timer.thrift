namespace java com.gsralex.gflow.core.thriftgen.timer

include "gflow.thrift"

struct TTimeReq{
    1:i64 id,
    2:i64 flowGroupId,
    3:string time
    4:bool active
    5:string accessToken
}

struct TDelTimerReq{
    1:i64 id,
    2:string accessToken
}

struct TSettingsReq{
    1:string key
    2:string value
    3:string accessToken
}

service TConfigService{
    gflow.TResp setSettings(1:TSettingsReq req);
    gflow.TResp addTimer(1:TTimeReq req);
    gflow.TResp updateTimer(1:TTimeReq req);
    gflow.TResp delTimer(1:TDelTimerReq req);
}