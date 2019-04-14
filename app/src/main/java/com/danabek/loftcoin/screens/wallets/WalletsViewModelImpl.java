package com.danabek.loftcoin.screens.wallets;

import android.app.Application;

import com.danabek.loftcoin.App;
import com.danabek.loftcoin.data.db.Database;
import com.danabek.loftcoin.data.db.model.CoinEntity;
import com.danabek.loftcoin.data.db.model.Wallet;
import com.danabek.loftcoin.data.db.model.WalletModel;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class WalletsViewModelImpl extends WalletsViewModel {
    private Database database;
    private CompositeDisposable disposables = new CompositeDisposable();

    public WalletsViewModelImpl(@NonNull Application application) {

        super(application);

        database = ((App) getApplication()).getDatabase();
    }

    private MutableLiveData<Object> selectCurrency = new MutableLiveData<>();
    private MutableLiveData<Boolean> walletsVisible = new MutableLiveData<>();
    private MutableLiveData<Boolean> newWalletVisible = new MutableLiveData<>();
    private MutableLiveData<List<WalletModel>> wallets = new MutableLiveData<>();

    @Override
    public LiveData<Object> selectCurrency() {
        return selectCurrency;
    }

    @Override
    public LiveData<Boolean> walletsVisible() {
        return walletsVisible;
    }

    @Override
    public LiveData<Boolean> newWalletVisible() {
        return newWalletVisible;
    }

    @Override
    public LiveData<List<WalletModel>> wallets() {
        return null;
    }

    @Override
    void getWallets() {
        Disposable disposable = database.getWallets()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        walletModels -> {
                            if (walletModels.isEmpty()) {
                                newWalletVisible.setValue(true);
                                walletsVisible.setValue(false);
                            } else {
                                newWalletVisible.setValue(false);
                                walletsVisible.setValue(true);

                                wallets.setValue(walletModels);
                            }
                        },
                        Timber::e
                );
        disposables.add(disposable);
    }

    @Override
    void onNewWalletClick() {

        selectCurrency.setValue(new Object());
    }

    @Override
    void onCurrencySelected(CoinEntity coinEntity) {
        Wallet wallet = randomWallet(coinEntity);

        Disposable disposable = Observable.fromCallable(() -> {
            database.saveWallet(wallet);
            return new Object();
        })
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {

                }, Timber::e);

        disposables.add(disposable);
    }

    private Wallet randomWallet(CoinEntity coin) {
        Random random = new Random();

        return new Wallet(UUID.randomUUID().toString(), coin.id, 10 * random.nextDouble());
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }
}
