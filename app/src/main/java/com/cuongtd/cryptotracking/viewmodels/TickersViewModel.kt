package com.cuongtd.cryptotracking.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.cuongtd.cryptotracking.models.Ticker
import com.cuongtd.cryptotracking.repositories.TickerRepository
import com.cuongtd.cryptotracking.utils.SortParams
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class TickersViewModel : ViewModel() {
    private val tickerRepository = TickerRepository()
    private val gson = Gson()
    private val tickerType = object : TypeToken<List<Ticker>>() {}.type

    var sortKey = liveData<SortParams> { SortParams.Default }
    var isDesc = liveData<Boolean> { true }

    var tickers: MutableLiveData<List<Ticker>> = MutableLiveData<List<Ticker>>()

    init {
        viewModelScope.launch() {
            tickers.value = tickerRepository.getTickers()
            tickerRepository.webSocketCreate(viewModelScope).asLiveData().observeForever {
                if (it != null) {
                    tickers.value = (gson.fromJson(
                        it.json,
                        tickerType
                    ) as List<Ticker>
                            ).plus(
                            tickers.value as List<Ticker>
                        )?.distinctBy { ticker -> ticker.symbol }
                    Log.d("test", tickers.value?.count().toString())
                }
            }
        }
    }
}
