package com.danabek.loftcoin.data.db.model;

import androidx.room.Entity;

@Entity
public class CoinEntity {

    public int id;

    public String name;

    public String symbol;

    public String slug;

    public String lastUpdated;
}
