package com.cuongtd.cryptotracking.ui.orderbook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.ui.theme.GainColor
import com.cuongtd.cryptotracking.ui.theme.LoseColor
import com.cuongtd.cryptotracking.utils.Helper

@Composable
fun OrderBookItem(it: List<String>) {
    Row(
        Modifier.padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(Modifier.weight(1f)) {
            Text(
                Helper.formatPrice(it[1]),
                fontSize = 13.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                it[0],
                fontSize = 13.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                color = GainColor
            )
        }
        Row(
            Modifier
                .weight(1f)
                .padding(start = 15.dp)
        ) {
            Text(
                it[0],
                fontSize = 13.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                color = LoseColor
            )
            Text(
                Helper.formatPrice(it[1]),
                fontSize = 13.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}
