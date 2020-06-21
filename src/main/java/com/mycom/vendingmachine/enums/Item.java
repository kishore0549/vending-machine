package com.mycom.vendingmachine.enums;

import lombok.Getter;

public enum Item {
    COKE(25),
    PEPSI(35),
    SODA(45);

    @Getter
    int price;

    Item(int price) {
        this.price = price;
    }
}
