package com.danabek.loftcoin.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Coin {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("symbol")
    public String symbol;

    @SerializedName("slug")
    public String slug;

    @SerializedName("last_updated")
    public String lastUpdated;

    @SerializedName("quote")
    public Map<String, Quote> quote;


}
