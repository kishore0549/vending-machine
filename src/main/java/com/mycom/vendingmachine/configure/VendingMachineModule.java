package com.mycom.vendingmachine.configure;

import com.google.inject.AbstractModule;
import com.mycom.vendingmachine.action.Order;
import com.mycom.vendingmachine.action.OrderBuilder;
import com.mycom.vendingmachine.inventory.CoinInventory;
import com.mycom.vendingmachine.inventory.Inventory;
import com.mycom.vendingmachine.inventory.impl.CoinInventoryImpl;
import com.mycom.vendingmachine.inventory.impl.ItemInventoryImpl;

public class VendingMachineModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Inventory.class).to(ItemInventoryImpl.class);
        bind(CoinInventory.class).to(CoinInventoryImpl.class);
        bind(Order.class).to(OrderBuilder.class);
    }
}
