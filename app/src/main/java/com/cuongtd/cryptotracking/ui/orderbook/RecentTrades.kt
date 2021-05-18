package com.cuongtd.cryptotracking.ui.orderbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.ui.theme.GainColor
import com.cuongtd.cryptotracking.ui.theme.LoseColor
import com.cuongtd.cryptotracking.utils.Helper
import com.cuongtd.cryptotracking.viewmodels.OrderBookViewModel

@Composable
fun RecentTrades(orderBookViewModel: OrderBookViewModel) {
    val recentTrade = orderBookViewModel.recentTrade.observeAsState()

    Column() {
        Row(
            Modifier
                .background(Color(0xFF1A212A))
                .padding(vertical = 5.dp, horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
        LazyColumn(Modifier.padding(horizontal = 15.dp)) {
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
}
