package com.example.demo.Exception;

public class HasBeenFoundException extends RuntimeException{
    private static final long serialVersionUID = -6074753940710869977L;
    public HasBeenFoundException(String message) {
        super(message + " has been used");
    }
}
