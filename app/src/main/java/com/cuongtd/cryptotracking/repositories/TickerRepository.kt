package com.cuongtd.cryptotracking.repositories

import com.cuongtd.cryptotracking.IWebSocketChannel
import com.cuongtd.cryptotracking.WebSocketChannel
import com.cuongtd.cryptotracking.models.RawData
import com.cuongtd.cryptotracking.models.SpotTicker
import com.cuongtd.cryptotracking.models.Ticker
import com.cuongtd.cryptotracking.services.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class TickerRepository {
    private lateinit var channel: IWebSocketChannel
    private val apiService = RetrofitBuilder.apiService

    fun webSocketCreate(scope: CoroutineScope): Flow<RawData> {
        channel = WebSocketChannel(scope)
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