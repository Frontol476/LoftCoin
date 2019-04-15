package com.danabek.loftcoin.data.db.model;

import io.realm.RealmObject;


public class Transaction extends RealmObject {


    public String walletId;

    public CoinEntity coin;

    public double amount;

    public long date;

    public Transaction(String walletId, double amount, long date, CoinEntity coin) {
        this.walletId = walletId;
        this.coin = coin;
        this.amount = amount;
        this.date = date;
    }
}
