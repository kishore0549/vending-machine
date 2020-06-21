package com.mycom.vendingmachine.exceptions;

public class NotSufficientChangeException extends Exception {

    private String msg;

    public NotSufficientChangeException(String msg) {
        super(msg);
        this.msg = msg;
    }


}
