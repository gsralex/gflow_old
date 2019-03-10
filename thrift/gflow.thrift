namespace java com.gsralex.gflow.pub.thriftgen

//调度结果
struct TResp{
    1:i32 code
    2:string msg
}


struct TReq{
    1:string accessToken
}

struct TIdReq{
    1:i64 id
    2:string accessToken;
}

exception TRespException{
    1: required i32 code
    2: optional string msg
}




