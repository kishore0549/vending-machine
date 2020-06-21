package com.mycom.vendingmachine.inventory.impl;

import com.mycom.vendingmachine.enums.Item;
import com.mycom.vendingmachine.exceptions.ItemInssuffientException;
import com.mycom.vendingmachine.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class ItemInventoryImpl implements Inventory {

    private static Map<Item, Integer> items = new HashMap<>();

    @Override
    public void addItem(Item item, Integer noOfItems) {
        items.put(item, noOfItems);
    }

    @Override
    public Item removeItem(Item item) {
        Integer total = items.get(item);
        Integer remaining = total - 1;
        items.put(item,remaining);
        return item;
    }

    @Override
    public Integer findInventory(String item) throws IllegalArgumentException {
        Item itemEnum =  Item.valueOf(item.toUpperCase());
        return items.get(itemEnum);
    }

    @Override
    public void printInventory() {
        System.out.println("*******Current Inventory of Items *********");
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            System.out.println(entry.getKey().toString() +" Items are in number :" + entry.getValue());
        }
        System.out.println();
    }

    @Override
    public Integer getPrice(String item) throws IllegalArgumentException {
        Item itemEnum =  Item.valueOf(item.toUpperCase());
      return itemEnum.getPrice();
    }
}
