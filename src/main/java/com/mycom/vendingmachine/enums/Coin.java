package com.mycom.vendingmachine.enums;

import lombok.Getter;

@Getter
public enum Coin {
    PENNY(1),
    NICKLE(5),
    DIME(10),
    QUARTER(25);

    private int amount;

    Coin(int amount) {
        this.amount= amount;
    }

}
