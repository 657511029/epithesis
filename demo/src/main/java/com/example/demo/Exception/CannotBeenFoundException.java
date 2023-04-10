package com.example.demo.Exception;

public class CannotBeenFoundException extends RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;
    public CannotBeenFoundException(String message){
        super(message + " can`t been found");
    }
}
