package com.enset.ebankingbackend.exceptions;

public class AppUserNotFoundException extends Exception{
    public AppUserNotFoundException(String message) {
        super(message);
    }
}
