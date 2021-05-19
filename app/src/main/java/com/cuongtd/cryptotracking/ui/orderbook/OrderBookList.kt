package com.cuongtd.cryptotracking.ui.orderbook

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cuongtd.cryptotracking.viewmodels.OrderBookViewModel

internal object Compare {
    fun max(a: List<String>, b: List<String>): List<String> {
        return if (a[1].toDouble() > b[1].toDouble()) a else b
    }
}

val LIMIT_DEPTH = 10  // Constants.API_RESULT_LIMIT

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun OrderBookList(orderBookViewModel: OrderBookViewModel) {
    val depth = orderBookViewModel.orderBook.observeAsState()
    var size = Math.min(Math.min(depth.value!!.b.count(), depth.value!!.a.count()), LIMIT_DEPTH)

    if (size == 0) return

    val bids = depth.value!!.b.take(size)
    val asks = depth.value!!.a.take(size)


    val maxBid = bids.reduce(Compare::max)[1].toDouble()
    val maxAsk = asks.reduce(Compare::max)[1].toDouble()

    Column {
        OrderBookListHeader()
        LazyColumn(
            Modifier
                .weight(1f)
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.Start
        ) {
            itemsIndexed(items = bids) { index, bid ->
                val ask = asks[index]
                OrderBookItem(bid, ask, maxBid, maxAsk)
            }
        }
    }
}