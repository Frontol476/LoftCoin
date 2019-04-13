package com.danabek.loftcoin.screens.currencies;

import com.danabek.loftcoin.data.db.model.CoinEntity;

public interface CurrenciesAdapterListener {
    void onCurrencyClick(CoinEntity coin);
}
