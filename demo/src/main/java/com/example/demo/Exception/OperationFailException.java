package com.example.demo.Exception;

public class OperationFailException extends RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;
    public OperationFailException(String message){
        super(message + " operation failed");
    }
}
