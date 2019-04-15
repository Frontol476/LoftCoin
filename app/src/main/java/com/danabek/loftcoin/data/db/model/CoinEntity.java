package com.danabek.loftcoin.data.db.model;

import com.danabek.loftcoin.utils.Fiat;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class CoinEntity extends RealmObject {

    @PrimaryKey
    public int id;

    public String name;

    public String symbol;

    public String slug;

    public String lastUpdated;

    public QuoteEntity usd;

    public QuoteEntity eur;

    public QuoteEntity rub;

    public QuoteEntity getQuote(Fiat fiat) {
        switch (fiat) {

            case USD:
                return usd;
            case EUR:
                return eur;
            case RUB:
                return rub;
        }

        return usd;
    }
}
