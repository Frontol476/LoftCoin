package com.danabek.loftcoin.data.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Coin")
public class CoinEntity {

    @PrimaryKey
    public int id;

    public String name;

    public String symbol;

    public String slug;

    public String lastUpdated;
}
