package com.cuongtd.cryptotracking.ui.tickers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.noRippleClickable
import com.cuongtd.cryptotracking.utils.SortParams
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel

@Composable
fun TickerListHeader(tickersViewModel: TickersViewModel) {
    Row(
        Modifier
            .height(25.dp)
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
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
                text = " / ",
                color = MaterialTheme.colors.onSecondary,
                fontSize = 10.sp,
                fontWeight = FontWeight.W300
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
