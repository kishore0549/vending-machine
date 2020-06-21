package com.mycom.vendingmachine.action;

import com.mycom.vendingmachine.enums.Coin;
import com.mycom.vendingmachine.enums.Item;

import java.util.List;
import java.util.Map;

public interface Order {
    void addItem(String item);
    void removeItem(String item);
    Integer totalValueOfKart();
    Boolean orderStatus();
    void orderSummary();
    void confirmOrder(Boolean orderStatus);
    List<Item> dispatchOrder();
    Integer getOrderInNumbers(Item item);
    void cleanOrder();

}
