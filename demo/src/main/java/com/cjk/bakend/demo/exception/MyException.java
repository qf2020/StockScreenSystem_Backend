package com.cjk.bakend.demo.exception;

public class MyException extends RuntimeException{
    private String erroMsg;

    public MyException(String message, Throwable cause) {
        super(message, cause);
        this.erroMsg = message;
    }

    public MyException(String message){
        super(message);
        this.erroMsg = message;
    }

    public String getErroMsg() {
        return erroMsg;
    }
}
