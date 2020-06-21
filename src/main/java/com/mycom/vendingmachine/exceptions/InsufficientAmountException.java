package com.mycom.vendingmachine.exceptions;

public class InsufficientAmountException extends Exception {

    private String msg;

    public InsufficientAmountException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
