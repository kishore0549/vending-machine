package com.mycom.vendingmachine.inventory.impl;

import com.mycom.vendingmachine.enums.Coin;
import com.mycom.vendingmachine.inventory.CoinInventory;

import java.util.HashMap;
import java.util.Map;

public class CoinInventoryImpl implements CoinInventory {
    Map<Coin, Integer> coinsBin = new HashMap<>();
    @Override
    public void addCoinstoBin(Coin coin, Integer noOfcoins) {
        Integer noOfCoinsinBin = coinsBin.get(coin);
        if (null == noOfCoinsinBin) noOfCoinsinBin =0;
        Integer total = noOfCoinsinBin + noOfcoins;
        coinsBin.put(coin,total);
    }

    @Override
    public void emptyBin() {
        coinsBin = new HashMap<>();
    }

    @Override
    public Integer calculateBin() {
        Integer total = 0;
        for (Map.Entry<Coin, Integer> entry : coinsBin.entrySet()) {
            Integer  value = ((Coin)entry.getKey()).getAmount() * entry.getValue();
            total = total + value;
        }

        return total;
    }

    @Override
    public void printInventory() {
        Integer totalValue = 0;
        System.out.println("*******Current Inventory of Coins *********");
        for (Map.Entry<Coin, Integer> entry : coinsBin.entrySet()) {
            System.out.println(entry.getKey().toString() +" Coins are in number :" + entry.getValue());
            totalValue = totalValue + entry.getKey().getAmount() * entry.getValue();
        }
        System.out.println();
        System.out.println("Total Amount of coins :" + totalValue);
    }

    @Override
    public Boolean hasCoin(Coin coin) {
        return coinsBin.containsKey(coin);
    }

    @Override
    public Coin removeCoinFromBin(Coin coin) {
        Integer noOfCoinsinBin = coinsBin.get(coin);
        if (noOfCoinsinBin >0) {
            Integer total = noOfCoinsinBin - 1;
            coinsBin.put(coin,total);
        }
        return coin;
    }


}
