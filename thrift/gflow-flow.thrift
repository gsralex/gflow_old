namespace java com.gsralex.gflow.pub.thriftgen.flow


include "gflow.thrift"



struct TFlowItem{
    1:string parameter
    2:string label
    3:i32 index
    4:i64 actionId
}


struct TFlowDirect{
    1:i32 index
    2:list<i32> nextIndex
}

struct TFlowGroup{
    1:i64 id,
    2:string name
    3:string description
    4:list<TFlowItem> itemList
    5:list<TFlowDirect> directList
}

service TFlowService{
    gflow.TResp setFlowGroup(1:TFlowGroup flowGroup);
}
