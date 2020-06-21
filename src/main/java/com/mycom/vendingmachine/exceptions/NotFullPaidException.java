package com.mycom.vendingmachine.exceptions;

public class NotFullPaidException extends Throwable {
    public NotFullPaidException(String msg) {
        super(msg);
    }
}
