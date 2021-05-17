package com.cuongtd.cryptotracking.viewmodels

import androidx.lifecycle.*
import com.cuongtd.cryptotracking.models.Trade
import com.cuongtd.cryptotracking.repositories.OrderBookRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class OrderBookViewModel(symbol: String) : ViewModel() {
    private val orderBookRepository = OrderBookRepository()
    private val gson = Gson()
    private val tradeType = object : TypeToken<Trade>() {}.type

    private var _recentTrades: MutableLiveData<List<Trade>> = MutableLiveData(listOf())
    val recentTrade: LiveData<List<Trade>>
        get() = _recentTrades

    init {
        viewModelScope.launch {
            _recentTrades.value = orderBookRepository.getRecentTrades(symbol)
            orderBookRepository.webSocketCreate(viewModelScope, symbol).asLiveData()
                .observeForever {
                    if (it != null) {
                        _recentTrades.value =
                            listOf(
                                gson.fromJson(
                                    it.json,
                                    tradeType
                                ) as Trade
                            ).plus(_recentTrades.value!!).take(30)
                    }
                }
        }
    }
}