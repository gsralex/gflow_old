package com.gsralex.gflow.core.rpc.client;

import com.gsralex.gflow.core.context.IpAddr;
import com.gsralex.gflow.core.rpc.protocol.RpcReq;
import com.gsralex.gflow.core.rpc.protocol.RpcResp;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gsralex
 * @version 2019/3/16
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResp> {

    private IpAddr remoteIp;
    private Channel channel;
    private Map<String, RpcFuture> rpcFutures = new ConcurrentHashMap<>();

    public RpcClientHandler() {
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
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        InetSocketAddress addr = (InetSocketAddress) ctx.channel().remoteAddress();
        remoteIp = new IpAddr(addr.getHostString(), addr.getPort());
    }



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResp resp) throws Exception {
        RpcFuture rpcFuture = rpcFutures.get(resp.getReqId());
        rpcFuture.setRpcResp(resp);
        rpcFuture.done();
        rpcFutures.remove(resp.getReqId());
    }

    public RpcFuture sendReq(RpcReq req) {
        RpcFuture rpcFuture = new RpcFuture();
        rpcFuture.setReqId(req.getReqId());
        rpcFutures.put(req.getReqId(), rpcFuture);
        channel.writeAndFlush(req);
        return rpcFuture;
    }

    public IpAddr getRemoteIp() {
        return remoteIp;
    }

    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);

    }
}
