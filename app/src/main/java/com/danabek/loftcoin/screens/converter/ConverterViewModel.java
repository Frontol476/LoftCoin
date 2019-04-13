package com.danabek.loftcoin.screens.converter;

import com.danabek.loftcoin.data.db.model.CoinEntity;

import io.reactivex.Observable;

public interface ConverterViewModel {

    Observable<String> sourceCurrency();

    Observable<String> destionationCurrency();

    Observable<String> destinationAmount();

    void onSourceAmountChange(String amount);

    void onSourceCurrencySelected(CoinEntity coin);

    void onDestinationCurrencySelected(CoinEntity coin);
}
