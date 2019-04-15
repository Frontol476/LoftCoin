package com.danabek.loftcoin.data.db.model.realm;

import com.danabek.loftcoin.data.db.Database;
import com.danabek.loftcoin.data.db.model.CoinEntity;
import com.danabek.loftcoin.data.db.model.Transaction;
import com.danabek.loftcoin.data.db.model.TransactionModel;
import com.danabek.loftcoin.data.db.model.Wallet;
import com.danabek.loftcoin.data.db.model.WalletModel;

import java.util.List;

import io.reactivex.Flowable;

public class DatabaseImplReal implements Database {
    @Override
    public void saveCoins(List<CoinEntity> coins) {

    }

    @Override
    public Flowable<List<CoinEntity>> getCoins() {
        return null;
    }

    @Override
    public CoinEntity getCoin(String symbol) {
        return null;
    }

    @Override
    public void saveWallet(Wallet wallet) {

    }

    @Override
    public Flowable<List<WalletModel>> getWallets() {
        return null;
    }

    @Override
    public void saveTransaction(List<Transaction> transactions) {

    }

    @Override
    public Flowable<List<TransactionModel>> getTransactions(String walletId) {
        return null;
    }
}
