package com.cuongtd.cryptotracking.models

import com.google.gson.annotations.SerializedName

data class Ticker(
    @SerializedName("e") var e: String,
    @SerializedName("E") var E: Long,
    @SerializedName("s") var s: String,
//    @SerializedName("p") var p: String,
    @SerializedName("P") var P: String,
    @SerializedName("w") var w: String,
    @SerializedName("x") var x: String,
    @SerializedName("c") var c: String,
//    @SerializedName("Q") var Q: String,
//    @SerializedName("b") var b: String,
    @SerializedName("B") var B: String,
    @SerializedName("a") var a: String,
//    @SerializedName("A") var A: String,
    @SerializedName("o") var o: String,
    @SerializedName("h") var h: String,
    @SerializedName("l") var l: String,
    @SerializedName("v") var v: String,
    @SerializedName("q") var q: String,
    @SerializedName("O") var O: Long,
    @SerializedName("C") var C: Long,
    @SerializedName("F") var F: Long,
    @SerializedName("L") var L: Long,
    @SerializedName("n") var n: Long

)