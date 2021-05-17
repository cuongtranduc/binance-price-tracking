package com.cuongtd.cryptotracking.models

import com.google.gson.annotations.SerializedName

data class Trade (
    @SerializedName("id", alternate=["t"]) var id : Long,
    @SerializedName("price", alternate=["p"]) var price : String,
    @SerializedName("qty", alternate=["q"]) var qty : String,
    @SerializedName("quoteQty") var quoteQty : String,
    @SerializedName("time", alternate=["E"]) var time : Long,
    @SerializedName("isBuyerMaker", alternate=["m"]) var isBuyerMaker : Boolean,
    @SerializedName("isBestMatch", alternate=["M"]) var isBestMatch : Boolean
)