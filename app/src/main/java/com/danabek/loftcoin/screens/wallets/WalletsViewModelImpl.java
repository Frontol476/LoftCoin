package com.danabek.loftcoin.screens.wallets;

import android.app.Application;

import com.danabek.loftcoin.App;
import com.danabek.loftcoin.data.db.Database;
import com.danabek.loftcoin.data.db.model.CoinEntity;
import com.danabek.loftcoin.data.db.model.Transaction;
import com.danabek.loftcoin.data.db.model.Wallet;

import java.util.ArrayList;
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
import timber.log.Timber;

public class WalletsViewModelImpl extends WalletsViewModel {
    private Database database;
    private CompositeDisposable disposables = new CompositeDisposable();

    public WalletsViewModelImpl(@NonNull Application application) {

        super(application);

        database = ((App) getApplication()).getDatabase();
        database.open();
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        database.close();
        super.onCleared();
    }

    private MutableLiveData<Object> selectCurrency = new MutableLiveData<>();
    private MutableLiveData<Boolean> walletsVisible = new MutableLiveData<>();
    private MutableLiveData<Boolean> newWalletVisible = new MutableLiveData<>();
    private MutableLiveData<List<Wallet>> wallets = new MutableLiveData<>();
    private MutableLiveData<List<Transaction>> transactions = new MutableLiveData<>();

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
    public LiveData<List<Wallet>> wallets() {
        return wallets;
    }

    @Override
    public LiveData<List<Transaction>> transactions() {
        return transactions;
    }


    @Override
    void getWallets() {
        Disposable disposable = database.getWallets()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        Wallets -> {
                            if (Wallets.isEmpty()) {
                                newWalletVisible.setValue(true);
                                walletsVisible.setValue(false);
                            } else {
                                newWalletVisible.setValue(false);
                                walletsVisible.setValue(true);

                                if (wallets.getValue() == null || wallets.getValue().isEmpty()) {
                                    Wallet model = Wallets.get(0);
                                    String walletId = model.walletId;
                                    getTransaction(walletId);
                                }

                                wallets.setValue(Wallets);
                            }
                        },
                        Timber::e
                );
        disposables.add(disposable);
    }

    private void getTransaction(String walletId) {
        Disposable disposable = database.getTransactions(walletId)
                .subscribe(
                        transactions -> this.transactions.setValue(transactions)
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
        List<Transaction> transactions = randomTransactions(wallet);

        Disposable disposable = Observable.fromCallable(() -> {
            database.saveWallet(wallet);
            database.saveTransaction(transactions);
            return new Object();
        })
                .subscribe(o -> {

                }, Timber::e);

        disposables.add(disposable);
    }

    @Override
    void onWalletsChanged(int position) {
        Wallet wallet = wallets.getValue().get(position);
        getTransaction(wallet.walletId);
    }

    private Wallet randomWallet(CoinEntity coin) {
        Random random = new Random();

        return new Wallet(UUID.randomUUID().toString(), 10 * random.nextDouble(), coin);
    }

    private List<Transaction> randomTransactions(Wallet wallet) {
        List<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            transactions.add(randomTransaction(wallet));
        }
        return transactions;
    }

    private Transaction randomTransaction(Wallet wallet) {
        Random random = new Random();

        long startDate = 1483228800000L;
        long nowDate = System.currentTimeMillis();
        long date = startDate + (long) (random.nextDouble() * (nowDate - startDate));

        double amount = 2 * random.nextDouble();
        boolean amountSign = random.nextBoolean();

        return new Transaction(wallet.walletId, amountSign ? amount : -amount, date, wallet.coin);
    }


}
