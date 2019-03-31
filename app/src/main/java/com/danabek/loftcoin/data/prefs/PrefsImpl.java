package com.danabek.loftcoin.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.danabek.loftcoin.utils.Fiat;

public class PrefsImpl implements Prefs {

    private static final String PREFS_NAME = "prefs";
    private static final String KEY_FIRST_LAUNCH = "first_launch";
    private static final String KEY_FIAT_CURRENCY = "fiat_currency";

    private Context context;

    public PrefsImpl(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isFirstLaunch() {
        return getSharedPreferences().getBoolean(KEY_FIRST_LAUNCH, true);
    }

    @Override
    public void setFirstLaunch(boolean firstLaunch) {
        getSharedPreferences().edit()
                .putBoolean(KEY_FIRST_LAUNCH, firstLaunch)
                .apply();
    }

    @Override
    public Fiat getFiatCurrency() {
        String currency = getSharedPreferences().getString(KEY_FIAT_CURRENCY, Fiat.USD.name());
        return Fiat.valueOf(currency);
    }

    @Override
    public void setFiatCurrency(Fiat fiat) {
        getSharedPreferences().edit()
                .putString(KEY_FIAT_CURRENCY, fiat.name())
                .apply();

    }
}
