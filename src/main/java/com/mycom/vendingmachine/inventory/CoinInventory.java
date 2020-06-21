package com.mycom.vendingmachine.inventory;

import com.mycom.vendingmachine.enums.Coin;

import java.util.List;

public interface CoinInventory {

    void addCoinstoBin(Coin coins, Integer noOfcoins);
    void emptyBin();
    Integer calculateBin();
    void printInventory();
    Boolean hasCoin(Coin coin);
    Coin removeCoinFromBin(Coin coin);
}
