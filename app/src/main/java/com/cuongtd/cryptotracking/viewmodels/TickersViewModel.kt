package com.cuongtd.cryptotracking.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuongtd.cryptotracking.models.Ticker
import com.cuongtd.cryptotracking.repositories.TickerRepository
import com.cuongtd.cryptotracking.ui.Tabs
import com.cuongtd.cryptotracking.utils.Constants
import com.cuongtd.cryptotracking.utils.SortParams
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TickersViewModel : ViewModel() {
    private val tickerRepository = TickerRepository()

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
            tickerRepository.createTickerStream(viewModelScope).collect {
                if (it != null) {
                    tickers.value = (allTickers + it).groupBy { it.symbol }.entries.map {
                        it.value.maxByOrNull { it.eventTime }!!
                    }
                    updateTickersByTab()
                    updateBasePrice()
                }
            }
        }
    }

    fun updateTickersByTab() {
        tickers.value = tickers.value?.filter {
            it.symbol.takeLast(3)
                .contains(_currentTab.value!!.symbol, ignoreCase = true)
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
            Tabs.BTC -> allTickers!!.find { it.symbol == Constants.BTCUSDT }?.lastPrice?.toDouble()
            Tabs.ETH -> allTickers!!.find { it.symbol == Constants.ETHUSDT }?.lastPrice?.toDouble()
            Tabs.BNB -> allTickers!!.find { it.symbol == Constants.BNBUSDT }?.lastPrice?.toDouble()
            else -> 1.0
        }
    }
}
