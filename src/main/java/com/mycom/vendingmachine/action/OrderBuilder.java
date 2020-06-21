package com.mycom.vendingmachine.action;

import com.mycom.vendingmachine.enums.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderBuilder implements  Order {
    private Map<Item, Integer> items = new HashMap<>();
    private Integer totalKartValue = 0;

    @Getter
    @Setter
    private Boolean status = false;

    private void addItem(Item item, Integer noOfItems) {
        int totalCount = 0;
        if(items.containsKey(item)) {
            totalCount = items.get(item);
            items.put(item, totalCount+noOfItems);

        } else {
            items.put(item, noOfItems);
        }

        totalKartValue = totalKartValue + item.getPrice() * noOfItems;
    }

    private void removeItem(Item item, Integer noOfItems) {

        Integer totalCount;
        if(items.containsKey(item)) {
            totalCount = items.get(item);
            if(totalCount > noOfItems) {
                items.put(item, totalCount - noOfItems);
                totalKartValue = totalKartValue - item.getPrice() * noOfItems;
            } else {
                items.remove(item);
            }
        }

    }

    @Override
    public void addItem(String item) throws IllegalArgumentException {
       Item itemEnum =  Item.valueOf(item.toUpperCase());
       addItem(itemEnum, 1);
    }


    @Override
    public void removeItem(String item) throws IllegalArgumentException {
        Item itemEnum =  Item.valueOf(item.toUpperCase());
        removeItem(itemEnum, 1);
    }

    @Override
    public Integer totalValueOfKart() {
        return totalKartValue;
    }

    @Override
    public Boolean orderStatus() {
        return this.getStatus();
    }

    @Override
    public void orderSummary() {

        Integer cokeNumber = this.items.get(Item.COKE);
        Integer pepsiNumber = this.items.get(Item.PEPSI);
        Integer sodaNumber = this.items.get(Item.SODA);
        System.out.println("Order Summary:");
        if(pepsiNumber != null)
        System.out.println("Item PEPSI ordered in number : "+ pepsiNumber + " Amount : "+ Item.PEPSI.getPrice() * pepsiNumber);

        if(cokeNumber != null)
        System.out.println("Item COKE ordered number : "+ cokeNumber + " Amount : "+ Item.COKE.getPrice()*cokeNumber);

        if(sodaNumber != null)
        System.out.println("Item SODA ordered number : "+ sodaNumber + " Amount : "+ Item.SODA.getPrice()*sodaNumber);

        System.out.println("Order status "+ (this.getStatus() ? "CONFIRMED" : "PENDING"));
    }

    @Override
    public void confirmOrder(Boolean status) {
        this.setStatus(status);
    }

    @Override
    public List<Item> dispatchOrder() {
        List<Item> itemsList = new ArrayList<>();
        synchronized (items) {
            Integer noOfPepsi = items.get(Item.PEPSI);
            Integer noOfCoke = items.get(Item.COKE);
            Integer noOfSoda= items.get(Item.SODA);
            if(null != noOfPepsi) {
               for ( int i=0;i< noOfPepsi; i++) {
                      itemsList.add(Item.PEPSI);
               }
            }

            if(null != noOfCoke) {
                for ( int i=0;i< noOfCoke; i++) {
                    itemsList.add(Item.COKE);
                }
            }

            if(null != noOfSoda) {
                for ( int i=0;i< noOfSoda; i++) {
                    itemsList.add(Item.SODA);
                }
            }
            totalKartValue = 0;
            items = new HashMap<>();
            status = false;
        }

        return itemsList;
    }

    @Override
    public Integer getOrderInNumbers(Item item) {
        return items.get(item);
    }

    @Override
    public void cleanOrder() {
        items = new HashMap<>();
        totalKartValue =0;
        status = false;
    }

}
