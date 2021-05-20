package com.cuongtd.cryptotracking.ui.orderbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.ui.theme.GainColor
import com.cuongtd.cryptotracking.ui.theme.LoseColor
import com.cuongtd.cryptotracking.utils.Helper

@Composable
fun OrderBookItem(bid: List<String>, ask: List<String>, maxBid: Double, maxAsk: Double) {
    val bidWidth = (bid[1].toDouble() / maxBid) * 100
    val askWidth = (ask[1].toDouble() / maxAsk) * 100

    Row(
        Modifier.height(25.dp),
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .weight(1f),
        ) {
            Row(
                Modifier.matchParentSize()
            ) {
                if (bidWidth != 100.0) {
                    Box(
                        modifier = Modifier
                            .weight((100 - bidWidth).toFloat())
                            .fillMaxHeight()
                    )
                }
                Box(
                    modifier = Modifier
                        .background(GainColor.copy(alpha = 0.1f))
                        .weight((bidWidth).toFloat())
                        .fillMaxHeight()
                )
            }
            Row(
                Modifier.matchParentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    Helper.formatPrice(bid[1]),
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Text(
                    Helper.formatOrderPrice(bid[0]),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 5.dp),
                    fontSize = 13.sp,
                    textAlign = TextAlign.End,
                    color = GainColor
                )
            }
        }
        Box(
            Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Row(
                Modifier.matchParentSize()
            ) {
                Box(
                    modifier = Modifier
                        .background(LoseColor.copy(alpha = 0.1f))
                        .weight((askWidth).toFloat())
                        .fillMaxHeight()
                )
                if (askWidth != 100.0) {
                    Box(
                        modifier = Modifier
                            .weight((100 - askWidth).toFloat())
                            .fillMaxHeight()
                    )
                }
            }
            Row(
                Modifier.matchParentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    Helper.formatOrderPrice(ask[0]),
                    fontSize = 13.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp),
                    textAlign = TextAlign.Start,
                    color = LoseColor
                )
                Text(
                    Helper.formatPrice(ask[1]),
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}
