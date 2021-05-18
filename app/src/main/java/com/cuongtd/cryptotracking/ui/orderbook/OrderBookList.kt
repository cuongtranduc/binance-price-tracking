package com.cuongtd.cryptotracking.ui.orderbook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cuongtd.cryptotracking.viewmodels.OrderBookViewModel

@Composable
fun OrderBookList(orderBookViewModel: OrderBookViewModel) {
    val orderBook = orderBookViewModel.orderBook.observeAsState()

    Column {
        OrderBookListHeader()
        LazyColumn(
            Modifier
                .weight(1f)
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.Start
        ) {
            items(items = orderBook.value!!.bids) {
                OrderBookItem(it)
            }
        }
    }
}