package com.danabek.loftcoin.data.db.model;


import io.realm.RealmObject;

public class Wallet extends RealmObject {

    public String walletId;
    public CoinEntity coin;
    public double amount;

    public Wallet(String walletId, double amount, CoinEntity coin) {
        this.walletId = walletId;
        this.coin = coin;
        this.amount = amount;
    }
}
