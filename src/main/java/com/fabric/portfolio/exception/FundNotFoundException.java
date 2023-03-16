package com.fabric.portfolio.exception;

public class FundNotFoundException extends RuntimeException {
    public FundNotFoundException(String message){
        super(message);
    }
}
