package com.gsralex.gflow.core.rpc.client;

import com.gsralex.gflow.core.context.IpAddr;
import io.netty.channel.EventLoopGroup;

/**
 * @author gsralex
 * @version 2019/3/19
 */
public class RpcClient {

    private EventLoopGroup eventLoopGroup;
    private IpAddr ip;


    public RpcClient(EventLoopGroup eventLoopGroup, IpAddr ip) {
        this.eventLoopGroup = eventLoopGroup;
        this.ip = ip;
    }

    public IpAddr getIp() {
        return ip;
    }

    public void close() {
        eventLoopGroup.shutdownGracefully();
    }
}
