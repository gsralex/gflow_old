package com.gsralex.gflow.core.rpc.client;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.rpc.protocol.RpcReq;
import com.gsralex.gflow.core.rpc.protocol.RpcResp;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResp> {

    private Channel channel;
    private IpAddr remoteIp;
    private Map<String, RpcFuture> rpcFutures = new ConcurrentHashMap<>();

    public RpcClientHandler(IpAddr remoteIp) {
        this.remoteIp = remoteIp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResp resp) throws Exception {
        RpcFuture rpcFuture = rpcFutures.get(resp.getReqId());
        rpcFuture.doReceived(resp);
        rpcFutures.remove(resp.getReqId());
    }

    public RpcFuture sendReq(RpcReq req) {
        RpcFuture rpcFuture = new RpcFuture();
        rpcFuture.setReqId(req.getReqId());
        rpcFutures.put(req.getReqId(), rpcFuture);
        channel.writeAndFlush(req);
        return rpcFuture;
    }

    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);

    }

    public IpAddr getRemoteIp() {
        return remoteIp;
    }
}
