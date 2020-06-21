package com.mycom.vendingmachine.inventory;

import com.mycom.vendingmachine.enums.Item;
import com.mycom.vendingmachine.exceptions.ItemInssuffientException;

public interface Inventory {
    void addItem(Item item, Integer noOfItems);
    Item removeItem(Item item);
    Integer findInventory(String item);
    void printInventory();
    Integer getPrice(String item) throws IllegalArgumentException;
}
