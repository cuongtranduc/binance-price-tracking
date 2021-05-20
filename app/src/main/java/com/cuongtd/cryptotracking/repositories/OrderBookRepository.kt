package com.cuongtd.cryptotracking.repositories

import com.cuongtd.cryptotracking.IWebSocketChannel
import com.cuongtd.cryptotracking.WebSocketChannel
import com.cuongtd.cryptotracking.models.OrderBook
import com.cuongtd.cryptotracking.models.OrderBookSnapshot
import com.cuongtd.cryptotracking.models.RawData
import com.cuongtd.cryptotracking.models.Trade
import com.cuongtd.cryptotracking.services.RetrofitBuilder
import com.cuongtd.cryptotracking.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class OrderBookRepository {
    private lateinit var channel: IWebSocketChannel
    private val apiService = RetrofitBuilder.apiService

    private val gson = Gson()
    private val tradeType = object : TypeToken<Trade>() {}.type
    private val depthType = object : TypeToken<OrderBook>() {}.type

    fun createHistoricalTradeStream(scope: CoroutineScope, symbol: String): Flow<Trade> {
        channel = WebSocketChannel(scope, "${Constants.WS_BASE_URL}${symbol.toLowerCase()}@trade")
        return channel.getIncoming().map { rawDataToHistoricalTrade(it) }
    }

    fun createDepthStream(scope: CoroutineScope, symbol: String): Flow<OrderBook> {
        channel = WebSocketChannel(scope, "${Constants.WS_BASE_URL}${symbol.toLowerCase()}@depth")
        return channel.getIncoming().map { rawDataToDepth(it) }
    }

    fun webSocketSend(data: RawData) {
        channel.send(data)
    }

    suspend fun getRecentTrades(symbol: String): List<Trade> {
        return withContext(Dispatchers.IO) {
            apiService.getRecentTrades(symbol).execute().body() ?: listOf()
        }
    }

    suspend fun getOrderBookSnapshot(symbol: String): OrderBookSnapshot {
        return withContext(Dispatchers.IO) {
            apiService.getOrderBookSnapshot(symbol).execute().body()!!
        }
    }

    private fun rawDataToHistoricalTrade(rawData: RawData): Trade {
        return gson.fromJson(
            rawData.json,
            tradeType
        )
    }

    private fun rawDataToDepth(rawData: RawData): OrderBook {
        return gson.fromJson(
            rawData.json,
            depthType
        )
    }
}