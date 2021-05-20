package com.cuongtd.cryptotracking.utils

class Constants {
    companion object {
        const val BASE_URL = "https://api.binance.com/api/"
        const val WS_BASE_URL = "wss://stream.binance.com:9443/ws/"
        const val WS_TICKER_URL = "wss://stream.binance.com:9443/ws/!ticker@arr"

        const val API_RESULT_LIMIT = 50
        const val DEPTH_LIMIT = 1000

        const val BTC = "BTC"
        const val ETH = "ETH"
        const val USDT = "SDT"
        const val BNB = "BNB"

        const val BTCUSDT = "BTCUSDT"
        const val ETHUSDT = "ETHUSDT"
        const val BNBUSDT = "BNBUSDT"
    }
}