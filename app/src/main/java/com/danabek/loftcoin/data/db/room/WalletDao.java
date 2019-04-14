package com.danabek.loftcoin.data.db.room;

import com.danabek.loftcoin.data.db.model.Transaction;
import com.danabek.loftcoin.data.db.model.TransactionModel;
import com.danabek.loftcoin.data.db.model.Wallet;
import com.danabek.loftcoin.data.db.model.WalletModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveWallet(Wallet wallet);

    @Query("SELECT w.*, c.* FROM Wallet w, Coin c WHERE w.currencyId = c.id")
    Flowable<List<WalletModel>> getWallets();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTransaction(List<Transaction> transactions);

    @Query("SELECT t.*, c.* FROM `Transaction` t,Coin c WHERE t.walletId =:walletId AND t.currencyId = c.id ORDER BY t.date DESC")
    Flowable<List<TransactionModel>> getTransaction(String walletId);
}
