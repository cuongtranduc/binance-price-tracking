package com.cuongtd.cryptotracking.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.noRippleClickable
import com.cuongtd.cryptotracking.ui.TickerCompose
import com.cuongtd.cryptotracking.utils.SortParams
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

@Composable
fun TickerListHeader(tickersViewModel: TickersViewModel) {
    Row(
        Modifier
            .background(Color(0xFF1A212A))
            .padding(horizontal = 15.dp, vertical = 5.dp)
    ) {
        Row(
            Modifier.weight(1f), horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                Modifier
                    .noRippleClickable() {
                        tickersViewModel.updateSortKey(SortParams.Pair)
                    },
            ) {
                TickListHeaderItem("Pair", SortParams.Pair, tickersViewModel)
            }
            Text(
                " / ", color = MaterialTheme.colors.onSecondary,
            )
            Row(
                Modifier
                    .noRippleClickable() {
                        tickersViewModel.updateSortKey(SortParams.Vol)

                    },
            ) {
                TickListHeaderItem("Vol", SortParams.Vol, tickersViewModel)
            }
        }
        Row(
            Modifier
                .weight(1f)
                .noRippleClickable() {
                    tickersViewModel.updateSortKey(SortParams.Price)

                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TickListHeaderItem("Last Price", SortParams.Price, tickersViewModel)
        }
        Row(
            Modifier
                .weight(1f)
                .noRippleClickable() {
                    tickersViewModel.updateSortKey(SortParams.Change)
                },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TickListHeaderItem("24h Chg", SortParams.Change, tickersViewModel)
        }
    }
}

@Composable
fun TickListHeaderItem(
    text: String,
    sortKey: SortParams,
    tickersViewModel: TickersViewModel
) {
    val currentSortKey by tickersViewModel.currentSortKey.observeAsState()
    val isSortDesc by tickersViewModel.isSortDesc.observeAsState()

    Text(
        text,
        fontSize = 13.sp,
        color = if (currentSortKey == sortKey) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
        textAlign = TextAlign.End
    )
    if (currentSortKey == sortKey && isSortDesc!!) {
        Icon(
            imageVector = Icons.Filled.ArrowUpward,
            contentDescription = "Change Percentage",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(start = 3.dp)
                .size(16.dp)
        )
    }
    if (tickersViewModel.currentSortKey.value == sortKey && !isSortDesc!!) {
        Icon(
            imageVector = Icons.Filled.ArrowDownward,
            contentDescription = "Change Percentage",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(start = 3.dp)
                .size(16.dp),
        )
    }
}