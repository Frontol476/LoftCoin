package com.danabek.loftcoin.data.db.model;

import com.danabek.loftcoin.data.api.model.Coin;
import com.danabek.loftcoin.data.api.model.Quote;
import com.danabek.loftcoin.data.api.model.QuoteEntity;
import com.danabek.loftcoin.utils.Fiat;

import java.util.ArrayList;
import java.util.List;

public class CoinEntityMapperIml implements CoinEntityMaper {
    @Override
    public List<CoinEntity> map(List<Coin> coins) {
        List<CoinEntity> entities = new ArrayList<>();

        for (Coin coin : coins) {
            entities.add(mapEntity(coin));
        }

        return entities;
    }

    private CoinEntity mapEntity(Coin coin) {
        CoinEntity entity = new CoinEntity();

        entity.id = coin.id;
        entity.name = coin.name;
        entity.symbol = coin.symbol;
        entity.slug = coin.slug;
        entity.lastUpdated = coin.lastUpdated;

        entity.usd = mapQuote(coin.quote.get(Fiat.USD.name()));
        entity.eur = mapQuote(coin.quote.get(Fiat.EUR.name()));
        entity.rub = mapQuote(coin.quote.get(Fiat.RUB.name()));

        return entity;
    }

    private QuoteEntity mapQuote(Quote quote) {
        if (quote == null) {
            return null;
        }

        QuoteEntity entity = new QuoteEntity();

        entity.price = quote.price;
        entity.percentChange1h = quote.percentChange1h;
        entity.percentChange24h = quote.percentChange24h;
        entity.percentChange7d = quote.percentChange7d;

        return entity;
    }
}
