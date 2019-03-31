package com.danabek.loftcoin;

import android.app.Application;

import com.danabek.loftcoin.data.api.Api;
import com.danabek.loftcoin.data.api.ApiInitializer;
import com.danabek.loftcoin.data.prefs.Prefs;
import com.danabek.loftcoin.data.prefs.PrefsImpl;

public class App extends Application {
    private Prefs prefs;
    private Api api;

    @Override
    public void onCreate() {
        super.onCreate();

        prefs = new PrefsImpl(this);
        api = new ApiInitializer().init();
    }

    public Prefs getPrefs() {

        return prefs;
    }

    public Api getApi() {
        return api;
    }
}
