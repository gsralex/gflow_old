package com.gsralex.gflow.pub.exception;

/**
 * @author gsralex
 * @version 2019/3/14
 */
public class GflowException extends RuntimeException {


    public GflowException() {
        super();
    }

    public GflowException(String message) {
        super(message);
    }

    public GflowException(String message, Throwable cause) {
        super(message, cause);
    }

}
