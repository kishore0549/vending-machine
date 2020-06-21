package com.mycom.test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mycom.vendingmachine.VendingMachine;
import com.mycom.vendingmachine.action.Order;
import com.mycom.vendingmachine.action.OrderBuilder;
import com.mycom.vendingmachine.configure.VendingMachineModule;
import com.mycom.vendingmachine.enums.Coin;
import com.mycom.vendingmachine.enums.Operation;
import com.mycom.vendingmachine.exceptions.ItemInssuffientException;
import com.mycom.vendingmachine.exceptions.NotFullPaidException;
import com.mycom.vendingmachine.exceptions.NotSufficientChangeException;
import com.mycom.vendingmachine.impl.VendingMachineImpl;
import com.mycom.vendingmachine.inventory.impl.ItemInventoryImpl;

import org.junit.Before;
import org.junit.Test;

public class MyFirstTest {

    static VendingMachine vendingMachine;
    @Test(expected = IllegalArgumentException.class)
    public void myfirstTest(){
        Order order = new OrderBuilder();
        order.addItem("abc");
        System.out.println(order.totalValueOfKart());

    }

    @Before
    public void configure() {
        final Injector injector = Guice.createInjector(new VendingMachineModule());
         vendingMachine = injector.getInstance(VendingMachineImpl.class);
    }


    @Test
    public void inventoryTest() {
        vendingMachine.printInventory();
    }

    @Test
    public void priceTest() {
        vendingMachine.selectItemAndGetPrice("soda");
    }

    @Test
    public void orderBuildTest() throws ItemInssuffientException {
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.orderSummary();
    }


    @Test
    public void orderBuildAddRemoveTest() throws ItemInssuffientException {
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.REMOVE.toString());
        vendingMachine.buildOrder("pepsi", Operation.ADD.toString());
        vendingMachine.orderSummary();
    }


    @Test
    public void orderTest() throws NotSufficientChangeException, NotFullPaidException, ItemInssuffientException {
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
/*        vendingMachine.buildOrder("coke", 4, Operation.ADD.toString());
        vendingMachine.buildOrder("pepsi", 1, Operation.ADD.toString());
        vendingMachine.buildOrder("soda", 4, Operation.ADD.toString());*/

        vendingMachine.orderSummary();

        vendingMachine.insertCoin(Coin.QUARTER);

        vendingMachine.orderSummary();

    }


    @Test
    public void orderWithExtraAmountTest() throws NotSufficientChangeException, NotFullPaidException, ItemInssuffientException {
        vendingMachine.buildOrder("soda", Operation.ADD.toString());

        vendingMachine.orderSummary();

        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);

        vendingMachine.orderSummary();

    }

    @Test(expected = ItemInssuffientException.class)
    public void orderWithInssuficientItemsTest() throws ItemInssuffientException {
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
    }


    @Test
    public void orderCancelledAmountRefundTest() throws ItemInssuffientException, NotSufficientChangeException, NotFullPaidException {
        vendingMachine.printInventory();
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());

        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.DIME);
        vendingMachine.insertCoin(Coin.NICKLE);
        vendingMachine.insertCoin(Coin.QUARTER);

        vendingMachine.confirmOrCancelOrder(Operation.CANCEL.toString());
        vendingMachine.printInventory();
    }


    @Test
    public void orderMultipleOperations() throws NotSufficientChangeException, NotFullPaidException, ItemInssuffientException {
        vendingMachine.printInventory();
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("pepsi", Operation.ADD.toString());
        vendingMachine.buildOrder("soda", Operation.ADD.toString());

        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.NICKLE);

        vendingMachine.confirmOrCancelOrder(Operation.CONFIRM.toString());
        vendingMachine.printInventory();

        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("pepsi", Operation.ADD.toString());
        vendingMachine.buildOrder("soda", Operation.ADD.toString());
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.NICKLE);
        vendingMachine.confirmOrCancelOrder(Operation.CONFIRM.toString());
        vendingMachine.printInventory();


        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.buildOrder("coke", Operation.ADD.toString());
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.confirmOrCancelOrder(Operation.CONFIRM.toString());
        vendingMachine.printInventory();

        try {
            vendingMachine.buildOrder("pepsi", Operation.ADD.toString());
            vendingMachine.buildOrder("pepsi", Operation.ADD.toString());
            vendingMachine.buildOrder("coke", Operation.ADD.toString());
            vendingMachine.insertCoin(Coin.QUARTER);
            vendingMachine.insertCoin(Coin.QUARTER);
            vendingMachine.insertCoin(Coin.QUARTER);
            vendingMachine.insertCoin(Coin.QUARTER);
            vendingMachine.insertCoin(Coin.QUARTER);
            vendingMachine.confirmOrCancelOrder(Operation.CONFIRM.toString());
            vendingMachine.printInventory();
        } catch (ItemInssuffientException iex) {

        }

        vendingMachine.buildOrder("pepsi", Operation.ADD.toString());
        vendingMachine.buildOrder("pepsi", Operation.ADD.toString());
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.confirmOrCancelOrder(Operation.CONFIRM.toString());
        vendingMachine.printInventory();


        vendingMachine.buildOrder("soda", Operation.ADD.toString());
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.printInventory();

        vendingMachine.buildOrder("soda", Operation.ADD.toString());
        vendingMachine.insertCoin(Coin.QUARTER);
        vendingMachine.confirmOrCancelOrder(Operation.CANCEL.toString());
        vendingMachine.printInventory();


        try {
            vendingMachine.buildOrder("soda", Operation.ADD.toString());
            vendingMachine.insertCoin(Coin.QUARTER);
            vendingMachine.confirmOrCancelOrder(Operation.CONFIRM.toString());
        }catch (NotFullPaidException nfp) {
            nfp.getStackTrace();
        }
        vendingMachine.printInventory();


            vendingMachine.buildOrder("soda", Operation.ADD.toString());
            vendingMachine.insertCoin(Coin.QUARTER);
            vendingMachine.insertCoin(Coin.QUARTER);
            vendingMachine.confirmOrCancelOrder(Operation.CONFIRM.toString());

        vendingMachine.printInventory();

        vendingMachine.resetVendingMachine();

        vendingMachine.printInventory();

    }
}