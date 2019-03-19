package com.gsralex.gflow.core.rpc.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author gsralex
 * @version 2019/3/17
 */
public class GenericEncoder extends MessageToByteEncoder {

    public Class<?> genericClass;

    public GenericEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf out) {
        if (genericClass.isInstance(o)) {
            byte[] bytes = ProtostuffUtils.serialize(o);
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
    }
}
