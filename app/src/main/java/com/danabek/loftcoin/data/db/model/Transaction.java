package com.danabek.loftcoin.data.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    public int transactionId;

    public String walletId;

    public int currencyId;

    public double amount;

    public long date;

    public Transaction(String walletId, int currencyId, double amount, long date) {
        this.walletId = walletId;
        this.currencyId = currencyId;
        this.amount = amount;
        this.date = date;
    }
}
