package com.cuongtd.cryptotracking.viewmodels

import androidx.lifecycle.*
import com.cuongtd.cryptotracking.models.OrderBook
import com.cuongtd.cryptotracking.models.OrderBookSnapshot
import com.cuongtd.cryptotracking.models.Trade
import com.cuongtd.cryptotracking.repositories.OrderBookRepository
import com.cuongtd.cryptotracking.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class OrderBookViewModel(symbol: String) : ViewModel() {
    private val orderBookRepository = OrderBookRepository()
    private val gson = Gson()
    private val tradeType = object : TypeToken<Trade>() {}.type
    private val depthType = object : TypeToken<OrderBook>() {}.type

    private var _recentTrades: MutableLiveData<List<Trade>> = MutableLiveData(listOf())
    val recentTrade: LiveData<List<Trade>>
        get() = _recentTrades

    private var orderBookSnapshot = OrderBookSnapshot(1, listOf(), listOf())

    private var _orderBook: MutableLiveData<OrderBook> =
        MutableLiveData(OrderBook("", 1, "", 1, 1, listOf(), listOf()))
    val orderBook: LiveData<OrderBook>
        get() = _orderBook

    var bids: List<List<String>> = listOf()
    var asks: List<List<String>> = listOf()

    init {
        viewModelScope.launch {
            createOrderBookStream(symbol)
            createHistoricalTradeStream(symbol)
        }
    }

    private suspend fun createOrderBookStream(symbol: String) {
        orderBookSnapshot = orderBookRepository.getOrderBookSnapshot(symbol)
        bids = orderBookSnapshot.bids
        asks = orderBookSnapshot.asks
        orderBookRepository.createDepthStream(viewModelScope, symbol).asLiveData()
            .observeForever {
                if (it != null) {
                    val newOrderBook =
                        gson.fromJson(
                            it.json,
                            depthType
                        ) as OrderBook
                    if (newOrderBook.FinalUpdateId > orderBookSnapshot.lastUpdateId) {
                        bids = _orderBook.value!!.b
                        asks = _orderBook.value!!.a
                        _orderBook.value = newOrderBook
                        _orderBook.value!!.b = (_orderBook.value!!.b + bids).distinctBy { it[0] }
                            .filter { it[1].toDouble() != 0.0 }
                        _orderBook.value!!.a = (_orderBook.value!!.a + asks).distinctBy { it[0] }
                            .filter { it[1].toDouble() != 0.0 }
                    }
                }
            }
    }

    private suspend fun createHistoricalTradeStream(symbol: String) {
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
                        ).plus(_recentTrades.value!!).take(Constants.API_RESULT_LIMIT)
                }
            }
    }
}