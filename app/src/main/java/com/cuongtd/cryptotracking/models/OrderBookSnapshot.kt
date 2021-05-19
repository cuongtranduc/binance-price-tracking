package com.cuongtd.cryptotracking.models

import com.google.gson.annotations.SerializedName

data class OrderBookSnapshot(
    @SerializedName("lastUpdateId") var lastUpdateId : Long,
    @SerializedName("bids") var bids : List<List<String>>,
    @SerializedName("asks") var asks : List<List<String>>
)