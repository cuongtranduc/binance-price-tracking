package com.cuongtd.cryptotracking.repositories

import com.cuongtd.cryptotracking.IWebSocketChannel
import com.cuongtd.cryptotracking.WebSocketChannel
import com.cuongtd.cryptotracking.models.RawData
import com.cuongtd.cryptotracking.models.Ticker
import com.cuongtd.cryptotracking.services.RetrofitBuilder
import com.cuongtd.cryptotracking.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class TickerRepository {
    private lateinit var channel: IWebSocketChannel
    private val apiService = RetrofitBuilder.apiService

    fun webSocketCreate(scope: CoroutineScope): Flow<RawData> {
        channel = WebSocketChannel(scope, Constants.WS_TICKER_URL)
        return channel.getIncoming()
    }

    fun webSocketSend(data: RawData) {
        channel.send(data)
    }

    suspend fun getTickers(): List<Ticker> {
        return withContext(Dispatchers.IO) {
            apiService.getTickers().execute().body()!!
        }
    }
}