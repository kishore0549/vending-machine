package com.mycom.vendingmachine.exceptions;

public class ItemInssuffientException extends Exception {

    ItemInssuffientException() {
        super();
    }

    public ItemInssuffientException(String message) {
        super(message);
    }
}
