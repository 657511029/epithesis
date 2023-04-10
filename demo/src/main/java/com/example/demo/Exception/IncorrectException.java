package com.example.demo.Exception;

public class IncorrectException extends RuntimeException {
    private static final long serialVersionUID = -6074753940710869977L;
    public IncorrectException(String message){
        super(message + " is incorrect");
    }
}
