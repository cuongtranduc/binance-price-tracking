package com.cuongtd.cryptotracking.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cuongtd.cryptotracking.noRippleClickable
import com.cuongtd.cryptotracking.ui.orderbook.OrderBookList
import com.cuongtd.cryptotracking.ui.orderbook.OrderBookTopBar
import com.cuongtd.cryptotracking.ui.orderbook.RecentTrades
import com.cuongtd.cryptotracking.viewmodels.OrderBookViewModel

enum class OrderTabs(val tab: String) {
    Order("order"), Trade("trade")
}

@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun OrderBookScreenCompose(symbol: String?, goBack: () -> Unit) {
    val orderBookViewModel = OrderBookViewModel(symbol!!)
    var currentTab = remember { mutableStateOf(OrderTabs.Order) }

    Scaffold(
        topBar = {
            OrderBookTopBar(symbol, goBack)
        }
    ) {
        Column {
            Row(
                Modifier
                    .background(MaterialTheme.colors.primaryVariant)
                    .height(35.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .noRippleClickable {
                            currentTab.value = OrderTabs.Order
                        }, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Orders",
                        color = if (currentTab.value == OrderTabs.Order) MaterialTheme.colors.primary
                        else MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight(600)
                    )
                    if (currentTab.value == OrderTabs.Order) {
                        Divider(
                            Modifier.padding(top = 2.dp),
                            color = MaterialTheme.colors.primary,
                            thickness = 2.dp
                        )
                    }
                }
                Column(
                    Modifier
                        .weight(1f)
                        .noRippleClickable {
                            currentTab.value = OrderTabs.Trade
                        }, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Historical Trades",
                        color = if (currentTab.value == OrderTabs.Trade) MaterialTheme.colors.primary
                        else MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight(600)
                    )
                    if (currentTab.value == OrderTabs.Trade) {
                        Divider(
                            Modifier.padding(top = 2.dp),
                            color = MaterialTheme.colors.primary,
                            thickness = 2.dp
                        )
                    }
                }
            }
            Crossfade(targetState = currentTab) {
                when (currentTab.value) {
                    OrderTabs.Order -> OrderBookList(orderBookViewModel)
                    OrderTabs.Trade -> RecentTrades(orderBookViewModel)
                }
            }
        }
    }
}



