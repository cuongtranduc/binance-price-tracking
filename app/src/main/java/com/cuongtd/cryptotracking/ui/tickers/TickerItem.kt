package com.cuongtd.cryptotracking.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.models.Ticker
import com.cuongtd.cryptotracking.noRippleClickable
import com.cuongtd.cryptotracking.ui.theme.GainColor
import com.cuongtd.cryptotracking.ui.theme.LoseColor
import com.cuongtd.cryptotracking.utils.Helper
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel

enum class PriceColor(val color: Color) {
    UnChanged(Color.Unspecified), Gain(GainColor), Lose(LoseColor)
}

@Composable
fun TickerCompose(
    ticker: Ticker,
    tickersViewModel: TickersViewModel,
    onNavigateOrderBook: (symbol: String) -> Unit,
) {
    val basePrice by tickersViewModel.basePrice.observeAsState()
    val tab by tickersViewModel.currentTab.observeAsState()
    val firstSymbol = remember { ticker.symbol.dropLast(tab!!.name.length) }
    var lastTicker by remember { mutableStateOf(ticker) }

    val priceColor by remember(ticker) {
        derivedStateOf {
            val color =
                if (ticker.lastPrice != lastTicker.lastPrice)
                    if (lastTicker.lastPrice <= ticker.lastPrice) PriceColor.Gain
                    else PriceColor.Lose
                else PriceColor.UnChanged
            lastTicker = ticker
            color
        }
    }

    Column {
        Row(
            modifier = Modifier
                .noRippleClickable {
                    onNavigateOrderBook(ticker.symbol)
                }
                .fillMaxSize()
                .padding(vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight(), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(firstSymbol, fontSize = 14.sp, fontWeight = FontWeight.W600)
                    Text(
                        " / ${tickersViewModel.currentTab.value!!.name}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W300,
                        color = MaterialTheme.colors.onSecondary
                    )
                }
                Text(
                    "Vol ${Helper.formatVolume(ticker.volume)}",
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300,
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
                Text(
                    Helper.formatPrice(ticker.lastPrice),
                    fontSize = 14.sp,
                    color = priceColor.color
                )
                Text(
                    "${Helper.formatPriceDouble(ticker.lastPrice.toDouble() * (basePrice ?: 0.0))}$",
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
            Column(
                Modifier
                    .weight(1f)
                    .padding(start = 30.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center,
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            (if (ticker.priceChange.toDouble() >= 0) Color(0xFF0C362F).copy(0.8f) else Color(
                                0xFF3A1B25
                            ).copy(0.8f))
                        )
                        .padding(vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        Helper.formatOrderPrice(ticker.priceChange),
                        color = if (ticker.priceChange.toDouble() >= 0) GainColor else LoseColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.W600,
                    )
                    Text(
                        Helper.formatPercentageChange(ticker.priceChangePercent),
                        color = if (ticker.priceChangePercent.toDouble() >= 0) GainColor else LoseColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W300,
                    )
                }
            }
        }
        Divider(color = Color(0xFF50555E).copy(0.3f), thickness = 0.2.dp)
    }
}