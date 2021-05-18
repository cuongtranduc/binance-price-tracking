package com.cuongtd.cryptotracking.ui.orderbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderBookListHeader() {
    Row(
        Modifier
            .height(30.dp)
            .background(Color(0xFF1A212A))
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Bids",
                fontSize = 13.sp,
                color = MaterialTheme.colors.onSecondary,

                textAlign = TextAlign.Start
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Asks",
                fontSize = 13.sp,
                color = MaterialTheme.colors.onSecondary,
                textAlign = TextAlign.Start
            )
        }
    }
}