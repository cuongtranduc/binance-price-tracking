package com.cuongtd.cryptotracking.models

import com.google.gson.annotations.SerializedName

data class OrderBook (
    @SerializedName("e") var e : String,
    @SerializedName("E") var E : Long,
    @SerializedName("s") var s : String,
    @SerializedName("U") var fistUpdateId : Long,
    @SerializedName("u") var FinalUpdateId : Long,
    @SerializedName("b") var b : List<List<String>>,
    @SerializedName("a") var a : List<List<String>>
)