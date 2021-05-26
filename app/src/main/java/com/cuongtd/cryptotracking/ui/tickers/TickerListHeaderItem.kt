package com.cuongtd.cryptotracking.ui.tickers

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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

    Text(
        text,
        fontSize = 10.sp,
        fontWeight = FontWeight.W300,
        color = if (currentSortKey == sortKey) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
        textAlign = TextAlign.End
    )
    if (currentSortKey == sortKey && isSortDesc!!) {
        Icon(
            imageVector = Icons.Filled.ArrowUpward,
            contentDescription = "Change Percentage",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(start = 3.dp)
                .size(14.dp)
        )
    }
    if (tickersViewModel.currentSortKey.value == sortKey && !isSortDesc!!) {
        Icon(
            imageVector = Icons.Filled.ArrowDownward,
            contentDescription = "Change Percentage",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(start = 3.dp)
                .size(14.dp),
        )
    }
}
