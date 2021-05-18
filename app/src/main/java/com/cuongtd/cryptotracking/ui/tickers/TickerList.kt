package com.cuongtd.cryptotracking.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.cuongtd.cryptotracking.ui.TickerCompose
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel

@Composable
fun TickerListCompose(tickersViewModel: TickersViewModel, onNavigateOrderBook: (symbol: String) -> Unit) {
    val tickers by tickersViewModel.tickers.observeAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(items = tickers!!) { ticker ->
            TickerCompose(ticker, tickersViewModel, onNavigateOrderBook)
        }
    }
}
