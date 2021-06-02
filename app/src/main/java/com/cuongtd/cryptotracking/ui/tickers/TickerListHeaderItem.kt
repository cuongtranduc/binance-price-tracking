package com.cuongtd.cryptotracking.ui.tickers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.utils.SortParams
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel

@Composable
fun TickListHeaderItem(
    text: String,
    sortKey: SortParams,
    tickersViewModel: TickersViewModel
) {
    val currentSortKey by tickersViewModel.currentSortKey.observeAsState()
    val isSortDesc by tickersViewModel.isSortDesc.observeAsState()

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text,
            fontSize = 10.sp,
            fontWeight = FontWeight.W300,
            color = if (currentSortKey == sortKey) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
            textAlign = TextAlign.End
        )
        Box() {
            Icon(
                imageVector = Icons.Filled.ArrowDropUp,
                contentDescription = "Change Percentage",
                tint = if (currentSortKey == sortKey && isSortDesc!!) MaterialTheme.colors.primary
                else MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
                    .padding(bottom = 7.dp)

            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Change Percentage",
                tint = if (tickersViewModel.currentSortKey.value == sortKey && !isSortDesc!!) MaterialTheme.colors.primary
                else MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .size(20.dp)
                    .padding(top = 7.dp)
            )
        }
    }
}
