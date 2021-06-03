package com.cuongtd.cryptotracking.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cuongtd.cryptotracking.ui.TickerCompose
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel

@Composable
fun TickerListCompose(
    tickersViewModel: TickersViewModel,
    onNavigateOrderBook: (String, String) -> Unit
) {
    val tickers by tickersViewModel.tickers.observeAsState()
    val isLoading by tickersViewModel.isLoading.observeAsState()

    if (isLoading!!) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            items(items = tickers!!) { ticker ->
                TickerCompose(ticker, tickersViewModel, onNavigateOrderBook)
            }
        }
    }
}