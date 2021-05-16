package com.cuongtd.cryptotracking.models

import com.google.gson.annotations.SerializedName

data class Ticker(
    @SerializedName("s", alternate=["symbol"]) var symbol: String,
    @SerializedName("P", alternate=["priceChangePercent"]) var priceChangePercent: String,
    @SerializedName("c", alternate=["lastPrice"]) var lastPrice: String,
    @SerializedName("v", alternate=["volume"]) var volume: String,
)

data class SpotTicker (
    @SerializedName("symbol") var symbol : String,
    @SerializedName("priceChangePercent") var priceChangePercent : String,
    @SerializedName("lastPrice") var lastPrice : String,
    @SerializedName("volume") var volume : String,
)