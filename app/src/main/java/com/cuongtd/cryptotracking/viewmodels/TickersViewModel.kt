package com.cuongtd.cryptotracking.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.cuongtd.cryptotracking.models.Ticker
import com.cuongtd.cryptotracking.repositories.TickerRepository
import com.cuongtd.cryptotracking.ui.Tabs
import com.cuongtd.cryptotracking.utils.SortParams
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class TickersViewModel : ViewModel() {
    private val tickerRepository = TickerRepository()
    private val gson = Gson()
    private val tickerType = object : TypeToken<List<Ticker>>() {}.type

    private val _isSortDesc = MutableLiveData(true)
    val isSortDesc: LiveData<Boolean>
        get() = _isSortDesc

    private val _currentSortKey = MutableLiveData(SortParams.Default)
    val currentSortKey: LiveData<SortParams>
        get() = _currentSortKey

    private val _currentTab = MutableLiveData(Tabs.BTC)
    val currentTab: LiveData<Tabs>
        get() = _currentTab

    var tickers: MutableLiveData<List<Ticker>> = MutableLiveData<List<Ticker>>(listOf())
    var allTickers: List<Ticker> = listOf()

    var basePrice = MutableLiveData<Double>(0.0)

    init {
        viewModelScope.launch() {
            allTickers = tickerRepository.getTickers()
            tickerRepository.webSocketCreate(viewModelScope).asLiveData().observeForever {
                if (it != null) {
                    tickers.value = (gson.fromJson(
                        it.json,
                        tickerType
                    ) as List<Ticker>
                            ).plus(
                            allTickers
                        )?.distinctBy { ticker -> ticker.symbol }
                    tickers.value = tickers.value!!.filter {
                        it.symbol.takeLast(3)
                            .contains(_currentTab.value!!.symbol, ignoreCase = true)
                    }
                    updateBasePrice()
                    applySort()
                }
            }
        }
    }

    fun updateCurrentTab(tab: Tabs) {
        viewModelScope.launch {
            tickers.value = allTickers.filter {
                it.symbol.takeLast(3).contains(tab.symbol, ignoreCase = true)
            }
            _currentTab.value = tab
            updateBasePrice()
            applySort();
        }
    }

    fun updateSortKey(key: SortParams) {
        viewModelScope.launch {
            _isSortDesc.value = if (_currentSortKey.value != key) true else !_isSortDesc.value!!
            _currentSortKey.value = key
            applySort()
        }
    }

    private fun applySort() {
        tickers.value = when (_currentSortKey.value) {
            SortParams.Default -> tickers.value
            SortParams.Pair -> if (!_isSortDesc.value!!) tickers.value?.sortedByDescending { it.symbol } else tickers.value?.sortedBy { it.symbol }
            SortParams.Vol -> if (!_isSortDesc.value!!) tickers.value?.sortedByDescending { it.volume.toDouble() } else tickers.value?.sortedBy { it.volume.toDouble() }
            SortParams.Price -> if (!_isSortDesc.value!!) tickers.value?.sortedByDescending { it.lastPrice.toDouble() } else tickers.value?.sortedBy { it.lastPrice.toDouble() }
            SortParams.Change -> if (!_isSortDesc.value!!) tickers.value?.sortedByDescending { it.priceChangePercent.toDouble() } else tickers.value?.sortedBy { it.priceChangePercent.toDouble() }
            else -> tickers.value
        }
    }

    private fun updateBasePrice() {
        basePrice.value = when (currentTab.value) {
            Tabs.BTC -> allTickers!!.find { it.symbol == "BTCUSDT" }?.lastPrice?.toDouble()
            Tabs.ETH -> allTickers!!.find { it.symbol == "ETHUSDT" }?.lastPrice?.toDouble()
            Tabs.BNB -> allTickers!!.find { it.symbol == "BNBUSDT" }?.lastPrice?.toDouble()
            else -> 1.0
        }
    }
}
