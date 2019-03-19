package com.gsralex.gflow.core.rpc.protocol;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class RpcReq {

    private String reqId;
    private String className;
    private String methodName;
    private Object[] parameters;
    private String accessToken;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

//    public static class Encoder extends MessageToByteEncoder {
//
//        @Override
//        protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf out) throws Exception {
//            if (o instanceof RpcReq) {
//                RpcReq req = (RpcReq) o;
//                ProtostuffUtils.writeString(out,req.getReqId());
//                ProtostuffUtils.writeString(out,req.getClassName());
//                ProtostuffUtils.writeString(out,req.getMethodName());
//
//                req.getParameters()
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
//            int length = in.readInt();
//
//            byte[] bytes = new byte[length];
//            in.getBytes(in.readerIndex(), bytes);
//            String msg = new String(bytes);
//            resp.setMsg(msg);
//            list.add(resp);
//        }
//    }

}
