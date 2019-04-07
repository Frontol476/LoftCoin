package com.danabek.loftcoin.screens.start;

import com.danabek.loftcoin.data.api.Api;
import com.danabek.loftcoin.data.api.model.Coin;
import com.danabek.loftcoin.data.api.model.RateResponse;
import com.danabek.loftcoin.data.db.Database;
import com.danabek.loftcoin.data.db.model.CoinEntity;
import com.danabek.loftcoin.data.db.model.CoinEntityMaper;
import com.danabek.loftcoin.data.prefs.Prefs;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class StartPresenterImpl implements StartPresenter {

    private Prefs prefs;
    private Api api;
    private Database database;
    private CoinEntityMaper coinEntityMaper;

    @Nullable
    private StartView view;

    public StartPresenterImpl(Prefs prefs, Api api, Database database, CoinEntityMaper coinEntityMaper) {
        this.prefs = prefs;
        this.api = api;
        this.database = database;
        this.coinEntityMaper = coinEntityMaper;
    }

    @Override
    public void attachView(StartView view) {
        this.view = view;

    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadRates() {

        Call<RateResponse> call = api.rates(api.CONVERT);
        call.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(@NotNull Call<RateResponse> call, @NotNull Response<RateResponse> response) {
                if (response.body() != null) {

                    List<Coin> coins = response.body().data;
                    List<CoinEntity> coinEntities = coinEntityMaper.map(coins);

                    database.saveCoins(coinEntities);
                }

                if (view != null) {
                    view.navigateToMainScreen();
                }
            }

            @Override
            public void onFailure(@NotNull Call<RateResponse> call, @NotNull Throwable t) {
                Timber.e(t);
            }
        });
    }
}
