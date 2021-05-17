package com.cuongtd.cryptotracking.services

import com.cuongtd.cryptotracking.models.Ticker
import com.cuongtd.cryptotracking.models.Trade
import com.cuongtd.cryptotracking.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v3/ticker/24hr")
    fun getTickers(): Call<List<Ticker>>

    @GET("v3/trades")
    fun getRecentTrades(@Query("symbol") symbol: String, @Query("limit") limit: Int = 30): Call<List<Trade>>
}

object RetrofitBuilder {
    private var BASE_URL = Constants.BASE_URL

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}
