package com.danabek.loftcoin.data.prefs;

import com.danabek.loftcoin.utils.Fiat;

public interface Prefs {
    boolean isFirstLaunch();

    void setFirstLaunch(boolean firstLaunch);

    Fiat getFiatCurrency();

    void setFiatCurrency(Fiat currency);

}
