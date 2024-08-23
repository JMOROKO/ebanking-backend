package com.enset.ebankingbackend.exceptions;

public class PasswordNotMatchException extends Exception{
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
