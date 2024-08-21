package com.enset.ebankingbackend.exceptions;

public class SameAccountException extends Exception{
    public SameAccountException(String message) {
        super(message);
    }
}
