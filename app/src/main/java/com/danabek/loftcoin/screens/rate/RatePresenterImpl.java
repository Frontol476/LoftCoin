package com.danabek.loftcoin.screens.rate;

import com.danabek.loftcoin.data.api.Api;
import com.danabek.loftcoin.data.api.model.Coin;
import com.danabek.loftcoin.data.db.Database;
import com.danabek.loftcoin.data.db.model.CoinEntity;
import com.danabek.loftcoin.data.db.model.CoinEntityMaper;
import com.danabek.loftcoin.data.prefs.Prefs;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RatePresenterImpl implements RatePresenter {

    private Prefs prefs;
    private Api api;
    private Database database;
    private CoinEntityMaper coinEntityMaper;

    @Nullable
    private RateView view;

    private CompositeDisposable disposables = new CompositeDisposable();

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
        disposables.dispose();

        view = null;
    }

    @Override
    public void getRate() {
        Disposable disposable = database.getCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coinEntities -> {
                    if (view != null) {
                        view.setCoins(coinEntities);
                    }
                }, Timber::e);
        disposables.add(disposable);
    }

    private void loadRate() {

        Disposable disposable = api.rates(Api.CONVERT)
                .subscribeOn(Schedulers.io())
                .map(rateResponse -> {
                    List<Coin> coins = rateResponse.data;
                    List<CoinEntity> coinEntities = coinEntityMaper.map(coins);
                    return coinEntities;
                })
                .doOnNext(coinEntities -> database.saveCoins(coinEntities))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coinEntities -> {
                            if (view != null) {
                                view.setRefreshing(false);
                            }

                        }, throwable -> {
                            Timber.e(throwable);
                            if (view != null) {
                                view.setRefreshing(false);
                            }
                        }
                );

        disposables.add(disposable);
    }

    @Override
    public void onRefresh() {

        loadRate();
    }
}
