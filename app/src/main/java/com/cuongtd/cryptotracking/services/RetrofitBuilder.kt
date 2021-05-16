package com.cuongtd.cryptotracking.services

import com.cuongtd.cryptotracking.models.Ticker
import com.cuongtd.cryptotracking.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("v3/ticker/24hr")
    fun getTickers(): Call<List<Ticker>>
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
