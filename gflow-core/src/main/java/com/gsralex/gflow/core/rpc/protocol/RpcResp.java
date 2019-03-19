package com.gsralex.gflow.core.rpc.protocol;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class RpcResp {

    private String reqId;
    private int code;
    private String msg;
    private Object data;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    //    public static class Encoder extends MessageToByteEncoder {
//
//        @Override
//        protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf out) throws Exception {
//            if (o instanceof RpcResp) {
//                RpcResp resp = (RpcResp) o;
//                out.writeInt(resp.getCode());
//                ProtostuffUtils.writeString(out, resp.getMsg());
//            }
//        }
//    }
//
//    public static class Decoder extends ByteToMessageDecoder {
//
//        @Override
//        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
//            RpcResp resp = new RpcResp();
//            resp.setCode(in.readInt());
//            resp.setMsg(ProtostuffUtils.readString(in));
//            list.add(resp);
//        }
//    }

}
