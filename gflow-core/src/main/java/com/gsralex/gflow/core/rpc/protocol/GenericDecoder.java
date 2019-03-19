package com.gsralex.gflow.core.rpc.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/17
 */
public class GenericDecoder extends ByteToMessageDecoder {


    private Class<?> genericClass;

    public GenericDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) {
        int len = in.readInt();
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        Object o = ProtostuffUtils.deserialize(bytes, genericClass);
        list.add(o);
    }
}
