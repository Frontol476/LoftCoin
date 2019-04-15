package com.danabek.loftcoin.screens.wallets;

import android.app.Application;

import com.danabek.loftcoin.data.db.model.CoinEntity;
import com.danabek.loftcoin.data.db.model.Transaction;
import com.danabek.loftcoin.data.db.model.Wallet;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


abstract class WalletsViewModel extends AndroidViewModel {

    public WalletsViewModel(@NonNull Application application) {
        super(application);
    }


    public abstract LiveData<Object> selectCurrency();

    public abstract LiveData<Boolean> walletsVisible();

    public abstract LiveData<Boolean> newWalletVisible();

    public abstract LiveData<List<Wallet>> wallets();

    public abstract LiveData<List<Transaction>> transactions();

    abstract void getWallets();


    abstract void onNewWalletClick();

    abstract void onCurrencySelected(CoinEntity coinEntity);

    abstract void onWalletsChanged(int position);
}
