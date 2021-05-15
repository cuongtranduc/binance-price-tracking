package com.cuongtd.cryptotracking.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.models.Ticker
import com.cuongtd.cryptotracking.ui.Tabs
import com.cuongtd.cryptotracking.ui.TickerCompose
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel

@Composable
fun TickerListCompose(tickersViewModel: TickersViewModel, tab: Tabs) {
    val tickers by tickersViewModel.tickers.observeAsState(listOf())
    val filteredTickers = tickers.filter {
        it.s.contains(tab.symbol, ignoreCase = true)
    }

    Column() {
        TickerListHeader()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(items = filteredTickers!!) { ticker ->
                TickerCompose(ticker)
            }
        }
    }
}

@Composable
fun TickerListHeader() {
    Row(
        Modifier
            .background(Color(0xFF1A212A))
            .padding(horizontal = 15.dp, vertical = 5.dp)
    ) {
        Text(
            "Pair / Vol",
            Modifier.weight(1f),
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSecondary,
            textAlign = TextAlign.Start
        )
        Text(
            "Last Price",
            Modifier.weight(1f),
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSecondary,
            textAlign = TextAlign.Center
        )
        Text(
            "24h Chg%",
            Modifier.weight(1f),
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSecondary,
            textAlign = TextAlign.End
        )
    }
}