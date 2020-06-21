package com.mycom.vendingmachine.impl;

import com.google.inject.Inject;
import com.mycom.vendingmachine.VendingMachine;
import com.mycom.vendingmachine.action.Order;
import com.mycom.vendingmachine.enums.Coin;
import com.mycom.vendingmachine.enums.Item;
import com.mycom.vendingmachine.exceptions.ItemInssuffientException;
import com.mycom.vendingmachine.exceptions.NotFullPaidException;
import com.mycom.vendingmachine.exceptions.NotSufficientChangeException;
import com.mycom.vendingmachine.inventory.CoinInventory;
import com.mycom.vendingmachine.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VendingMachineImpl implements VendingMachine {

    Inventory itemInventory;
    CoinInventory coinInventory;
    Order order;
    Integer totalCoinValue = 0;
    List<Coin> refundAmount;

    @Inject
    public VendingMachineImpl(Inventory itemInventory, Order order, CoinInventory coinInventory) {
        this.itemInventory = itemInventory;
        this.order = order;
        this.coinInventory=coinInventory;
        initialize();
    }

    private void initialize() {

        for(Coin c : Coin.values()) {
            coinInventory.addCoinstoBin(c, 5);
        }
        for(Item i : Item.values()) {
            itemInventory.addItem(i, 5);
        }
    }

    @Override
    public void printInventory() {
        coinInventory.printInventory();
        itemInventory.printInventory();
    }


    @Override
    public Integer selectItemAndGetPrice(String item) {
        Integer price = itemInventory.getPrice(item);
        ;
        System.out.println("Item ("+ item.toString().toUpperCase() + ") price is :"+ price);

        return price;
    }

    @Override
    public void buildOrder(String item, String op) throws ItemInssuffientException {

        Integer inventoryNumbers = itemInventory.findInventory(item);
        if (inventoryNumbers <= 0 )  {
            System.out.println("Order cancelled. Please try again with below items");
            itemInventory.printInventory();
            order.cleanOrder();
            throw new ItemInssuffientException("Order can not be fulfilled right now. Currently we have only "+ inventoryNumbers + " of "+ item.toUpperCase());
        }

        switch (op) {
            case "ADD" : {
                System.out.println("Item "+ item.toUpperCase() +" added to kart.");
                if (canAcceptOrder(inventoryNumbers, item)) {
                    order.addItem(item);
                }
            } break;
            case "REMOVE" : order.removeItem(item); break;
            default : System.out.println("Total Kart value :" + order.totalValueOfKart()); break;
        }
    }

    private Boolean canAcceptOrder(Integer inventory, String item) {
        Integer orderInNumbers = order.getOrderInNumbers(Item.valueOf(item.toUpperCase()));
        if (null == orderInNumbers || inventory > orderInNumbers) {
            return true;
        }
        return false;
    }

    @Override
    public void orderSummary() {
        this.order.orderSummary();
    }

    @Override
    public void insertCoin(Coin coin) throws NotSufficientChangeException, NotFullPaidException {

        if (order.totalValueOfKart() > 0 & !order.orderStatus()) {
            System.out.println("Amount inserted : " + coin.getAmount());
            totalCoinValue = totalCoinValue + coin.getAmount();
            if( order.totalValueOfKart() == totalCoinValue) {
                order.confirmOrder(true);
                coinInventory.addCoinstoBin(coin, 1);
                dispenseOrder();
            }
            else if (totalCoinValue > order.totalValueOfKart()) {
                order.confirmOrder(true);
/*                Integer balanceToBeRefunded = totalCoinValue - order.totalValueOfKart();
                List<Coin> coinsToBeRefunded = getChange(balanceToBeRefunded);*/
                dispenseOrder();
            } else if (totalCoinValue < order.totalValueOfKart()) {
                coinInventory.addCoinstoBin(coin, 1);
                System.out.println("Amount to be inserted : " + (order.totalValueOfKart() - totalCoinValue));
            }
        }
    }

    @Override
    public void confirmOrCancelOrder(String op) throws NotSufficientChangeException, NotFullPaidException {

        switch (op) {
            case "CONFIRM":
                dispenseOrder();
                break;
            case "CANCEL": {
                System.out.println("Cancelling the order of value : "+ order.totalValueOfKart());
                if (totalCoinValue > 0) {
                    refundAmount = getChange(totalCoinValue);
                    refund();
                    order.cleanOrder();
                    reset();
                }
            } break;
        }
    }

    private boolean hasSufficientChange() {
        return hasSufficientChangeForAmount(totalCoinValue - order.totalValueOfKart());
    }

    private boolean hasSufficientChangeForAmount(Integer amount) {
        boolean hasChange = true;
        try {
            refundAmount = getChange(amount);
        } catch (NotSufficientChangeException nsce) {
            hasChange = false;
        }
        return hasChange;
    }

    private void dispenseOrder() throws NotSufficientChangeException, NotFullPaidException {
        if (isFullyPaid()) {
            if (hasSufficientChange()) {
                List<Item> orderedItems = order.dispatchOrder();
                orderedItems.stream().forEach(item -> {
                    System.out.println(item.toString() +" dispensed");
                    itemInventory.removeItem(item);
                });
                refund();
                reset();
            } else {
                throw new NotSufficientChangeException("Not Sufficient change in Inventory");
            }
        } else {
            refundAmount = getChange(totalCoinValue);
            refund();
            String msg = "Total order value fully not paid , Order canceled. Amount refunded: "+ totalCoinValue;
            reset();
            order.cleanOrder();
            throw new NotFullPaidException(msg);
        }
    }


    private List<Coin> getChange(Integer amount) throws NotSufficientChangeException{
        List<Coin> changes = Collections.EMPTY_LIST;

        if(amount > 0){
            System.out.println("Getting change for refund: " + amount);
            changes = new ArrayList<Coin>();
            long balance = amount;
            while(balance > 0){
                if(balance >= Coin.QUARTER.getAmount()
                        && coinInventory.hasCoin(Coin.QUARTER)){
                    changes.add(Coin.QUARTER);
                    balance = balance - Coin.QUARTER.getAmount();
                    continue;

                }else if(balance >= Coin.DIME.getAmount()
                        && coinInventory.hasCoin(Coin.DIME)) {
                    changes.add(Coin.DIME);
                    balance = balance - Coin.DIME.getAmount();
                    continue;

                }else if(balance >= Coin.NICKLE.getAmount()
                        && coinInventory.hasCoin(Coin.NICKLE)) {
                    changes.add(Coin.NICKLE);
                    balance = balance - Coin.NICKLE.getAmount();
                    continue;

                }else if(balance >= Coin.PENNY.getAmount()
                        && coinInventory.hasCoin(Coin.PENNY)) {
                    changes.add(Coin.PENNY);
                    balance = balance - Coin.PENNY.getAmount();
                    continue;

                }else{
                    throw new NotSufficientChangeException("NotSufficientChange, Please try another product");
                }
            }
        }

        return changes;
    }

    private void refund() {
        if (refundAmount != null && refundAmount.size()>0) {
            refundAmount.stream().forEach(coin -> {
                coinInventory.removeCoinFromBin(coin);
                System.out.println("Amount refunded:" + coin.getAmount());
            });
        }
    }

    private Boolean isFullyPaid() {
        if (totalCoinValue < order.totalValueOfKart()) {
            return false;
        }
        return true;
    }


    private void reset() {
         totalCoinValue = 0;
         refundAmount = null;
    }

    @Override
    public void resetVendingMachine() {
        coinInventory.emptyBin();
        initialize();
    }


}
