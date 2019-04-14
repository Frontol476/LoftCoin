package com.danabek.loftcoin.data.db;

import com.danabek.loftcoin.data.db.model.CoinEntity;
import com.danabek.loftcoin.data.db.model.Wallet;
import com.danabek.loftcoin.data.db.model.WalletModel;

import java.util.List;

import io.reactivex.Flowable;

public interface Database {
    void saveCoins(List<CoinEntity> coins);

    Flowable<List<CoinEntity>> getCoins();

    CoinEntity getCoin(String symbol);

    void saveWallet(Wallet wallet);

    Flowable<List<WalletModel>> getWallets();
}
