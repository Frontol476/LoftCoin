package com.danabek.loftcoin.data.db.model;

import io.realm.RealmObject;

public class QuoteEntity extends RealmObject {

    public double price;


    public double percentChange1h;


    public double percentChange24h;


    public double percentChange7d;


}
