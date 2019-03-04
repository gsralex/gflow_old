package com.gsralex.gflow.web;

import com.gsralex.gflow.web.handles.actions.ActionListHandler;
import com.gsralex.gflow.web.handles.actions.SaveActionHandler;
import com.gsralex.gflow.web.handles.timer.TimerListHandler;
import com.gsralex.gflow.web.handles.UrlLocation;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author gsralex
 * @version 2019/2/26
 */
public class HttpServer {

    private int port;
    private UrlLocation urlLocation = new UrlLocation();

    public HttpServer(int port) {
        this.port = port;
    }

    public void serve() throws InterruptedException {
        registerUrlHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ChannelInitializer<SocketChannel> initializer = new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast(new HttpRequestDecoder())
                            .addLast(new HttpResponseEncoder())
                            .addLast(new HttpObjectAggregator(512 * 1024))
                            .addLast(new HttpServerHandler(urlLocation));
                }
            };
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(initializer)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    private void registerUrlHandler() {
        //timer
        urlLocation.addHttpHandler(new TimerListHandler());
        //action
        urlLocation.addHttpHandler(new SaveActionHandler());
        urlLocation.addHttpHandler(new ActionListHandler());
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 20001;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }
        HttpServer server = new HttpServer(port);
        server.serve();
    }
}
