package com.gsralex.scheduler.client;

/**
 * @author gsralex
 * @version 2018/12/13
 */
public class ClientTransportException extends RuntimeException {

    public ClientTransportException(String message) {
        super(message);
    }

    public ClientTransportException(Throwable cause) {
        super(cause);
    }

    public ClientTransportException(String message, Throwable cause) {
        super(message, cause);
    }

}
