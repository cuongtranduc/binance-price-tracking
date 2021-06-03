package com.cuongtd.cryptotracking.ui

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.models.Ticker
import com.cuongtd.cryptotracking.ui.orderbook.OrderBookList
import com.cuongtd.cryptotracking.ui.orderbook.OrderBookTopBar
import com.cuongtd.cryptotracking.ui.orderbook.RecentTrades
import com.cuongtd.cryptotracking.ui.theme.GainColor
import com.cuongtd.cryptotracking.ui.theme.LoseColor
import com.cuongtd.cryptotracking.utils.Helper
import com.cuongtd.cryptotracking.viewmodels.OrderBookViewModel
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun OrderBookScreenCompose(
    tickersViewModel: TickersViewModel,
    symbol: String?,
    tickerJson: String?,
    goBack: () -> Unit
) {
    val orderBookViewModel = OrderBookViewModel(symbol!!)
    val baseSymbol = tickersViewModel.currentTab
    val firstSymbol = symbol.dropLast(baseSymbol.value!!.name.length)

    val tickerType = object : TypeToken<Ticker>() {}.type
    val ticker: Ticker by orderBookViewModel.ticker.observeAsState(
        Gson().fromJson(
            tickerJson,
            tickerType
        )
    )

    Scaffold(
        topBar = {
            OrderBookTopBar("$firstSymbol / ${baseSymbol.value}", goBack)
        }
    ) {
        Column {
            LegendData(ticker)
            Column() {
                OrderBookList(orderBookViewModel)
            }
            Column(Modifier.weight(1f)) {
                RecentTrades(orderBookViewModel)
            }
        }
    }
}

@Composable
fun LegendData(ticker: Ticker) {
    var lastTicker by remember { mutableStateOf(ticker) }

    val priceColor by remember(ticker) {
        derivedStateOf {
            val color =
                if (ticker.lastPrice != lastTicker.lastPrice)
                    if (lastTicker.lastPrice <= ticker.lastPrice) PriceColor.Gain
                    else PriceColor.Lose
                else PriceColor.UnChanged
            lastTicker = ticker
            color
        }
    }

    Column(
        Modifier
            .padding(vertical = 10.dp, horizontal = 15.dp)
            .fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = Helper.formatOrderPrice(ticker.lastPrice),
                color = priceColor.color,
                fontSize = 28.sp,
                fontWeight = FontWeight.W600
            )
            Column(Modifier.padding(start = 10.dp)) {
                Text(
                    ticker.priceChange,
                    color = if (ticker.priceChange.toDouble() >= 0) GainColor else LoseColor,
                    fontSize = 12.sp
                )
                Text(
                    Helper.formatPercentageChange(ticker.priceChangePercent),
                    color = if (ticker.priceChange.toDouble() >= 0) GainColor else LoseColor,
                    fontSize = 12.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                Text(
                    "Open",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colors.onBackground
                )
                Text(ticker.openPrice, fontSize = 12.sp, color = MaterialTheme.colors.onSecondary)
            }
            Column(
                Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "High",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colors.onBackground
                )
                Text(ticker.highPrice, fontSize = 12.sp, color = MaterialTheme.colors.onSecondary)
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                Text(
                    "Low",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colors.onBackground
                )
                Text(ticker.lowPrice, fontSize = 12.sp, color = MaterialTheme.colors.onSecondary)
            }
        }
    }
}


