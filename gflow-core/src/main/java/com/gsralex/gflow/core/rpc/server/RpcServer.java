package com.gsralex.gflow.core.rpc.server;

import com.gsralex.gflow.core.rpc.protocol.GenericDecoder;
import com.gsralex.gflow.core.rpc.protocol.GenericEncoder;
import com.gsralex.gflow.core.rpc.protocol.RpcReq;
import com.gsralex.gflow.core.rpc.protocol.RpcResp;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class RpcServer {

    private static Logger LOG = LoggerFactory.getLogger(RpcServer.class);
    private Map<String, Object> serviceHandles = new HashMap<>();

    public void registerHandler(Class clazz, Object impl) {
        serviceHandles.put(clazz.getName(), impl);
    }

    public void serve(int port) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                //用定长处理粘包问题
                                .addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
//                                .addLast(new LineBasedFrameDecoder(1024))
//                                .addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                                .addLast(new GenericDecoder(RpcReq.class))
                                .addLast(new GenericEncoder(RpcResp.class))
                                .addLast(new RpcServerHandler(serviceHandles));
                    }
                })
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture cf = bootstrap.bind(port).sync();
            LOG.info("Server served by port {}", port);
            cf.channel().closeFuture().sync();
        } finally {
            System.out.println("123123");
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
