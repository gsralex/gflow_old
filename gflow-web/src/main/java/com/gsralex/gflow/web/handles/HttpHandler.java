package com.gsralex.gflow.web.handles;

/**
 * @author gsralex
 * @version 2019/2/26
 */
public interface HttpHandler<REQ, RESP> {

    RESP doAction(REQ req);

    String getRequestPath();
}
