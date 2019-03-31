package com.danabek.loftcoin.data.api.model;

import com.google.gson.annotations.SerializedName;

public class Quote {
    @SerializedName("price")
    public double price;

    @SerializedName("percent_change_1h")
    public double percentChange1h;

    @SerializedName("percent_change_24h")
    public double percentChange24h;

    @SerializedName("percent_change_7d")
    public double percentChange7d;
}
