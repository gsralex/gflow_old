# gflow
分布式流程调度
适用于大数据流程调度，工作流调度开发
![blockchain](https://upload-images.jianshu.io/upload_images/1366612-329bbcadd90ea89a.png "gflow")


### scheduler
scheduler即工作流调度端
scheduler支持主从结构，在一个scheduler集群中仅有一台是master状态，其他均为slave。默认采用配置，指定master服务器ip，也可以通过zookeeper进行选主，默认先启动的scheduler为master，在master失效后，其他slave会争抢master，依次类推。

### executor
executor
executor采用tag标记，具有相同tag标记的executor为提供功能一致的executor，在executor端，scheduler的主从结构对于executor来说是透明的。
