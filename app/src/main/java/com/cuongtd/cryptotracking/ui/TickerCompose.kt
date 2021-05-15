package com.cuongtd.cryptotracking.ui

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.models.Ticker
import com.cuongtd.cryptotracking.ui.theme.GainColor
import com.cuongtd.cryptotracking.ui.theme.LoseColor
import com.cuongtd.cryptotracking.utils.Helper

@Composable
fun TickerCompose(ticker: Ticker) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight(), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(ticker.s)
                Text(
                    "Vol ${Helper.formatVolume(ticker.v)}",
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(Helper.formatPrice(ticker.c))
            }
            Row(
                Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    Helper.formatPercentageChange(ticker.P),
                    color = if (ticker.P.toDouble() >= 0) GainColor else LoseColor
                )
                Icon(
                    imageVector = if (ticker.P.toDouble() >= 0) Icons.Filled.TrendingUp else Icons.Filled.TrendingDown,
                    contentDescription = "Change Percentage",
                    tint = if (ticker.P.toDouble() >= 0) GainColor else LoseColor,
                    modifier = Modifier.padding(start = 3.dp)
                )
            }
        }
        Divider(color = MaterialTheme.colors.onSecondary, thickness = 0.5.dp)
    }
}