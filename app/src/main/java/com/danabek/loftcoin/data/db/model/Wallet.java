package com.danabek.loftcoin.data.db.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Wallet {

    @PrimaryKey
    @NonNull
    public String walletId;
    public int currencyId;
    public double amount;

    public Wallet(@NonNull String walletId, int currencyId, double amount) {
        this.walletId = walletId;
        this.currencyId = currencyId;
        this.amount = amount;
    }
}
