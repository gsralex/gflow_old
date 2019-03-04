package com.gsralex.gflow.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gsralex.gflow.web.handles.HttpHandler;
import com.gsralex.gflow.web.handles.UrlLocation;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * @author gsralex
 * @version 2019/2/26
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    private static final Charset ENCODING = StandardCharsets.UTF_8;
    private UrlLocation urlLocation;
    private Gson gson = new Gson();

    public HttpServerHandler(UrlLocation urlLocation) {
        this.urlLocation = urlLocation;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;
            QueryStringDecoder decoder = new QueryStringDecoder(req.uri(), ENCODING);
            HttpHandler handler = urlLocation.getHttpHandle(decoder.path());
            if (handler != null) {
                String json = "";
                if (req.method() == HttpMethod.GET) {
                    HttpParam httpParam = new HttpParam(decoder.uri());
                    Object actionReq = getReqByUrl(handler, httpParam);
                    Object actionResp = handler.doAction(actionReq);
                    json = gson.toJson(actionResp);
                } else if (req.method() == HttpMethod.POST) {
                    ByteBuf bf = req.content();
                    byte[] byteArray = new byte[bf.capacity()];
                    bf.readBytes(byteArray);
                    String content = new String(byteArray);
                    Object actionReq = getReqByContent(handler, content);
                    Object actionResp = handler.doAction(actionReq);
                    json = gson.toJson(actionResp);
                }
                writeJson(ctx, json);
            } else {
                writeHtml(ctx, "<h3>gflow</h3>");
            }
        }
    }

    private Class getReqClass(HttpHandler handler) {
        Class clazz = handler.getClass();
        clazz.getGenericSuperclass();
        Type[] types = clazz.getGenericInterfaces();
        Type[] typeArgs = ((ParameterizedType) types[0]).getActualTypeArguments();
        Type reqType = typeArgs[0];
        return (Class) reqType;
    }

    private Object getReqByContent(HttpHandler handler, String content) {
        Class reqClass = getReqClass(handler);
        return gson.fromJson(content, reqClass);
    }

    private Object getReqByUrl(HttpHandler handler, HttpParam param) {
        Class reqClass = getReqClass(handler);
        JsonObject jsonObject = new JsonObject();
        for (String key : param.keySet()) {
            jsonObject.addProperty(key, param.getString(key));
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, reqClass);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        String html = "<h3>gflow</h3>";
        html += "<span>error : " + cause.getMessage() + "</span>";
        writeHtml(ctx, html);
    }


    private void writeHtml(ChannelHandlerContext ctx, String html) {
        write(ctx, html, "text/html");
    }

    private void writeJson(ChannelHandlerContext ctx, String json) {
        write(ctx, json, HttpHeaderValues.APPLICATION_JSON.toString());
    }

    private void write(ChannelHandlerContext ctx, String respContent, String contentType) {
        DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(respContent.getBytes()));
        HttpHeaders headers = resp.headers();
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, true);
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.add(HttpHeaderNames.CONTENT_TYPE, contentType + ";charset=" + ENCODING.toString());
        headers.add(HttpHeaderNames.CONTENT_LENGTH, resp.content().readableBytes());
        headers.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        ctx.write(resp);
        ctx.flush();
    }
}
