package com.cuongtd.cryptotracking.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.ui.theme.GainColor
import com.cuongtd.cryptotracking.ui.theme.LoseColor
import com.cuongtd.cryptotracking.utils.Helper
import com.cuongtd.cryptotracking.viewmodels.OrderBookViewModel

@Composable
fun OrderBookScreenCompose(symbol: String?, goBack: () -> Unit) {
    val orderBookViewModel = OrderBookViewModel(symbol!!)
    Scaffold(
        topBar = {
            topBar(symbol, goBack)
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)) {
            Row() {
                OrderBook()
            }
            Column() {
                RecentTrades(orderBookViewModel)
            }
        }
    }
}

@Composable
fun OrderBook() {

}

@Composable
fun RecentTrades(orderBookViewModel: OrderBookViewModel) {
    val recentTrade = orderBookViewModel.recentTrade.observeAsState()

    Row() {
        Text(
            "Time",
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        Text(
            "Price",
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Center
        )
        Text(
            "Quantity",
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
    LazyColumn() {
        items(items = recentTrade.value!!) {
            Row(Modifier.padding(vertical = 3.dp)) {
                Text(
                    Helper.formatTradeTime(it.time.toString()),
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Text(
                    Helper.formatPrice(it.price),
                    fontSize = 13.sp,
                    modifier = Modifier.weight(2f),
                    textAlign = TextAlign.Center,
                    color = if (it.isBuyerMaker) LoseColor else GainColor
                )
                Text(
                    Helper.formatQuantity(it.qty),
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
fun topBar(symbol: String, goBack: () -> Unit) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        navigationIcon = {
            IconButton(
                onClick = {
                    goBack()
                },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
        title = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    symbol,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Center
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.Equalizer,
                    null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
    )
}