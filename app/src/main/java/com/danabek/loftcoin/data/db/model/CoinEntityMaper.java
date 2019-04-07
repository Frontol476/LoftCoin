package com.danabek.loftcoin.data.db.model;

import com.danabek.loftcoin.data.api.model.Coin;

import java.util.List;

public interface CoinEntityMaper {
    List<CoinEntity> map(List<Coin> coins);
}
