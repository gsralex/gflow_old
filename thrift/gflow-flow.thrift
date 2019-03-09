namespace java com.gsralex.gflow.pub.thriftgen.flow


include "gflow.thrift"


//任务组描述
struct TGroupJobReq{
    1:i64 groupId,
    2:string parameter
    3:string accessToken
}
