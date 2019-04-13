package com.danabek.loftcoin.screens.converter;

import com.danabek.loftcoin.data.db.model.CoinEntity;

public interface CurrenciesBottomSheetListener {
    void onCurrencySelected(CoinEntity coin);
}
