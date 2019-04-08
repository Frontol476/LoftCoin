package com.danabek.loftcoin.data.db;

import com.danabek.loftcoin.data.db.model.CoinEntity;

import java.util.List;

import io.reactivex.Flowable;

public interface Database {
    void saveCoins(List<CoinEntity> coins);

    Flowable<List<CoinEntity>> getCoins();
}
