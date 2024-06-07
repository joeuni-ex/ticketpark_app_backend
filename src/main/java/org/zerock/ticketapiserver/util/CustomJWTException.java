package org.zerock.ticketapiserver.util;

public class CustomJWTException extends RuntimeException{

    public CustomJWTException(String msg){

        super(msg);

    }
}
