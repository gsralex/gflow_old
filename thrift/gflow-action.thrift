namespace java com.gsralex.gflow.pub.thriftgen.action

include "gflow.thrift"

struct TActionReq{
    1:i64 id,
    2:string name,
    3:string className,
    4:string tag,
    5:string accessToken
}


struct TAction{
    1:i64 id,
    2:string name,
    3:string className,
    4:string tag,
    5:i64 createTime
}

struct TActionIdReq{
    1:i64 id
    2:string accessToken
}

struct TActionResp{
    1:TAction action,
    2:i32 code
}

struct TActionListResp{
    1:list<TAction> actionList,
    2:i32 cnt
}




service TActionService{
    gflow.TResp saveAction(1:TActionReq req);
    gflow.TResp updateAction(1:TActionReq req);
    gflow.TResp removeAction(1:gflow.TIdReq req);
    TActionResp getAction(1:gflow.TIdReq req);
    TActionListResp listAction(1:i32 pageSize,2:i32 pageIndex);
}