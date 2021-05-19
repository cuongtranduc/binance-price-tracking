package com.cuongtd.cryptotracking.repositories

import com.cuongtd.cryptotracking.IWebSocketChannel
import com.cuongtd.cryptotracking.WebSocketChannel
import com.cuongtd.cryptotracking.models.OrderBookSnapshot
import com.cuongtd.cryptotracking.models.RawData
import com.cuongtd.cryptotracking.models.Trade
import com.cuongtd.cryptotracking.services.RetrofitBuilder
import com.cuongtd.cryptotracking.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class OrderBookRepository {
    private lateinit var channel: IWebSocketChannel
    private val apiService = RetrofitBuilder.apiService

    fun webSocketCreate(scope: CoroutineScope, symbol: String): Flow<RawData> {
        channel = WebSocketChannel(scope, "${Constants.WS_BASE_URL}${symbol.toLowerCase()}@trade")
        return channel.getIncoming()
    }

    fun createDepthStream(scope: CoroutineScope, symbol: String): Flow<RawData> {
        channel = WebSocketChannel(scope, "${Constants.WS_BASE_URL}${symbol.toLowerCase()}@depth")
        return channel.getIncoming()
    }

    fun webSocketSend(data: RawData) {
        channel.send(data)
    }

    suspend fun getRecentTrades(symbol: String): List<Trade> {
        return withContext(Dispatchers.IO) {
            apiService.getRecentTrades(symbol).execute().body()!!
        }
    }

    suspend fun getOrderBookSnapshot(symbol: String): OrderBookSnapshot {
        return withContext(Dispatchers.IO) {
            apiService.getOrderBookSnapshot(symbol).execute().body()!!
        }
    }
}