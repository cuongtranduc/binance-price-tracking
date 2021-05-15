package com.cuongtd.cryptotracking.viewmodels

import androidx.lifecycle.*
import com.cuongtd.cryptotracking.TickerRepository
import com.cuongtd.cryptotracking.models.Ticker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TickersViewModel : ViewModel() {
    private val tickerRepository = TickerRepository()
    private val gson = Gson()
    private val tickerType = object : TypeToken<List<Ticker>>() {}.type

    var tickers: MutableLiveData<List<Ticker>> = MutableLiveData<List<Ticker>>()

    init {
        tickerRepository.webSocketCreate(viewModelScope).asLiveData().observeForever {
            if (it != null) {
                viewModelScope.launch {
                    tickers.value = gson.fromJson(it.json, tickerType)
                }
            }
        }
    }
}