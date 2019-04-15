package com.danabek.loftcoin;

import android.app.Application;

import com.danabek.loftcoin.data.api.Api;
import com.danabek.loftcoin.data.api.ApiInitializer;
import com.danabek.loftcoin.data.db.Database;
import com.danabek.loftcoin.data.db.DatabaseInitializer;
import com.danabek.loftcoin.data.prefs.Prefs;
import com.danabek.loftcoin.data.prefs.PrefsImpl;

import timber.log.Timber;

public class App extends Application {
    private Prefs prefs;
    private Api api;
    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        prefs = new PrefsImpl(this);
        api = new ApiInitializer().init();
        new DatabaseInitializer().init(this);
    }

    public Prefs getPrefs() {

        return prefs;
    }

    public Api getApi() {
        return api;
    }

    public Database getDatabase() {
        return database;
    }
}

