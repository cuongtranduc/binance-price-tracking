package com.cuongtd.cryptotracking.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.models.Ticker
import com.cuongtd.cryptotracking.ui.theme.GainColor
import com.cuongtd.cryptotracking.ui.theme.LoseColor
import com.cuongtd.cryptotracking.utils.Helper
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel

@Composable
fun TickerCompose(
    ticker: Ticker,
    tickersViewModel: TickersViewModel,
    onNavigateOrderBook: (symbol: String) -> Unit
) {
    val basePrice by tickersViewModel.basePrice.observeAsState()
    val tab by tickersViewModel.currentTab.observeAsState()
    val firstSymbol = ticker.symbol.dropLast(tab!!.name.length)

    Column {
        Row(
            modifier = Modifier
                .clickable {
                    onNavigateOrderBook(ticker.symbol)
                }
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight(), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(firstSymbol, fontSize = 16.sp, fontWeight = FontWeight.W500)
                    Text(
                        " / ${tickersViewModel.currentTab.value!!.name}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onSecondary
                    )
                }
                Text(
                    "Vol ${Helper.formatVolume(ticker.volume)}",
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 12.sp,
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
                Text(Helper.formatPrice(ticker.lastPrice), fontSize = 14.sp)
                Text(
                    "${Helper.formatPriceDouble(ticker.lastPrice.toDouble() * (basePrice ?: 0.0))}$",
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
            Row(
                Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    Helper.formatPercentageChange(ticker.priceChangePercent),
                    color = if (ticker.priceChangePercent.toDouble() >= 0) GainColor else LoseColor,
                    fontSize = 14.sp
                )
                Icon(
                    imageVector = if (ticker.priceChangePercent.toDouble() >= 0) Icons.Filled.TrendingUp else Icons.Filled.TrendingDown,
                    contentDescription = "Change Percentage",
                    tint = if (ticker.priceChangePercent.toDouble() >= 0) GainColor else LoseColor,
                    modifier = Modifier.padding(start = 3.dp)
                )
            }
        }
//        Divider(color = MaterialTheme.colors.onSecondary, thickness = 0.5.dp)
    }
}