package com.danabek.loftcoin.data.db.room;

import com.danabek.loftcoin.data.db.model.CoinEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCoins(List<CoinEntity> coins);

    @Query("SELECT * FROM Coin")
    List<CoinEntity> getCoins();
}
