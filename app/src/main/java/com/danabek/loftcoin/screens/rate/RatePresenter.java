package com.danabek.loftcoin.screens.rate;

import com.danabek.loftcoin.utils.Fiat;

public interface RatePresenter {
    void attachView(RateView view);

    void detachView();

    void getRate();

    void onRefresh();

    void onMenuItemCurrencyClick();

    void onFiatCurrencySelected(Fiat currency);

    void onRateLongClick(String symbol);
}
