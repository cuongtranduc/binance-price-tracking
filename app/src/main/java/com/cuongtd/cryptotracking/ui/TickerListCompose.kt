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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.noRippleClickable
import com.cuongtd.cryptotracking.ui.Tabs
import com.cuongtd.cryptotracking.ui.TickerCompose
import com.cuongtd.cryptotracking.utils.SortParams
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel

@Composable
fun TickerListCompose(tickersViewModel: TickersViewModel, tab: Tabs) {
    var sortKey = remember { mutableStateOf(SortParams.Default) }
    var isDesc = remember { mutableStateOf(true) }

    val tickers by tickersViewModel.tickers.observeAsState(listOf())
    val filteredTickers = tickers.filter {
        it.symbol.takeLast(3).contains(tab.symbol, ignoreCase = true)
    }

    val sortedList =
        when (sortKey.value) {
            SortParams.Default -> filteredTickers
            SortParams.Pair -> if (!isDesc.value) filteredTickers.sortedByDescending { it.symbol } else filteredTickers.sortedBy { it.symbol }
            SortParams.Vol -> if (!isDesc.value) filteredTickers.sortedByDescending { it.volume.toDouble() } else filteredTickers.sortedBy { it.volume.toDouble() }
            SortParams.Price -> if (!isDesc.value) filteredTickers.sortedByDescending { it.lastPrice.toDouble() } else filteredTickers.sortedBy { it.lastPrice.toDouble() }
            SortParams.Change -> if (!isDesc.value) filteredTickers.sortedByDescending { it.priceChangePercent.toDouble() } else filteredTickers.sortedBy { it.priceChangePercent.toDouble() }
        }

    Column() {
        TickerListHeader(sortKey, isDesc)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(items = sortedList!!) { ticker ->
                TickerCompose(ticker)
            }
        }
    }
}

@Composable
fun TickerListHeader(sortKey: MutableState<SortParams>, isDesc: MutableState<Boolean>) {
    Row(
        Modifier
            .background(Color(0xFF1A212A))
            .padding(horizontal = 15.dp, vertical = 7.dp)
    ) {
        Row(Modifier.weight(1f)) {
            Row(
                Modifier
                    .noRippleClickable() {
                        isDesc.value =
                            if (sortKey.value != SortParams.Pair) true else !isDesc.value
                        sortKey.value = SortParams.Pair
                    },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TickListHeaderItem("Pair", SortParams.Pair, sortKey, isDesc)
            }
            Text(
                " / ", color = MaterialTheme.colors.onSecondary,
            )
            Row(
                Modifier
                    .noRippleClickable() {
                        isDesc.value =
                            if (sortKey.value != SortParams.Vol) true else !isDesc.value
                        sortKey.value = SortParams.Vol
                    },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TickListHeaderItem("Vol", SortParams.Vol, sortKey, isDesc)
            }
        }
        Row(
            Modifier
                .weight(1f)
                .noRippleClickable() {
                    isDesc.value = if (sortKey.value != SortParams.Price) true else !isDesc.value
                    sortKey.value = SortParams.Price
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TickListHeaderItem("Last Price", SortParams.Price, sortKey, isDesc)
        }
        Row(
            Modifier
                .weight(1f)
                .noRippleClickable() {
                    isDesc.value = if (sortKey.value != SortParams.Change) true else !isDesc.value
                    sortKey.value = SortParams.Change
                },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TickListHeaderItem("24h Chg", SortParams.Change, sortKey, isDesc)
        }
    }
}

@Composable
fun TickListHeaderItem(
    text: String,
    sortKey: SortParams,
    currentSortKey: MutableState<SortParams>,
    isDesc: MutableState<Boolean>
) {

    Text(
        text,
        fontSize = 14.sp,
        color = if (currentSortKey.value == sortKey) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
        textAlign = TextAlign.End
    )
    if (currentSortKey.value == sortKey && isDesc.value) {
        Icon(
            imageVector = Icons.Filled.ArrowUpward,
            contentDescription = "Change Percentage",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(start = 3.dp)
                .size(16.dp)
        )
    }
    if (currentSortKey.value == sortKey && !isDesc.value) {
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