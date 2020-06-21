package com.mycom.vendingmachine;

import com.mycom.vendingmachine.enums.Coin;
import com.mycom.vendingmachine.exceptions.ItemInssuffientException;
import com.mycom.vendingmachine.exceptions.NotFullPaidException;
import com.mycom.vendingmachine.exceptions.NotSufficientChangeException;

public interface VendingMachine {

     Integer selectItemAndGetPrice(String item);
     void insertCoin(Coin coin) throws NotSufficientChangeException, NotFullPaidException;
     void printInventory();
     void buildOrder(String item, String op) throws ItemInssuffientException;
     void orderSummary();
     void confirmOrCancelOrder(String op) throws NotSufficientChangeException, NotFullPaidException;
     void resetVendingMachine();

}
