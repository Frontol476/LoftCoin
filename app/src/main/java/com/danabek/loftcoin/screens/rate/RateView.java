package com.danabek.loftcoin.screens.rate;

import com.danabek.loftcoin.data.db.model.CoinEntity;

import java.util.List;

public interface RateView {
    void setCoins(List<CoinEntity> coins);

    void setRefreshing(Boolean refreshing);

    void showCurrencyDialog();
}
