package com.danabek.loftcoin.screens.rate;

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

public class RatePresenterImpl implements RatePresenter {

    private Prefs prefs;
    private Api api;
    private Database database;
    private CoinEntityMaper coinEntityMaper;

    @Nullable
    private RateView view;

    public RatePresenterImpl(Prefs prefs, Api api, Database database, CoinEntityMaper coinEntityMaper) {
        this.prefs = prefs;
        this.api = api;
        this.database = database;
        this.coinEntityMaper = coinEntityMaper;
    }

    @Override
    public void attachView(RateView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void getRate() {
        List<CoinEntity> coins = database.getCoins();

        if (view != null) {
            view.setCoins(coins);
        }
    }

    private void loadRate() {
        Call<RateResponse> call = api.rates(api.CONVERT);
        call.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(@NotNull Call<RateResponse> call, @NotNull Response<RateResponse> response) {
                RateResponse body = response.body();


                if (response.body() != null) {

                    List<Coin> coins = response.body().data;
                    List<CoinEntity> coinEntities = coinEntityMaper.map(coins);

                    database.saveCoins(coinEntities);

                    if (view != null) {
                        view.setCoins(coinEntities);
                    }
                }
                if (view != null) {
                    view.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NotNull Call<RateResponse> call, @NotNull Throwable t) {
                if (view != null) {
                    view.setRefreshing(false);
                }
                Timber.e(t);
            }
        });
    }

    @Override
    public void onRefresh() {

        loadRate();
    }
}
