package com.danabek.loftcoin.data.db.model;

import androidx.room.Embedded;

public class WalletModel {

    @Embedded()
    public Wallet wallet;

    @Embedded()
    public CoinEntity coin;
}
