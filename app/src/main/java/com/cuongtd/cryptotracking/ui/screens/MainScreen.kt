package com.cuongtd.cryptotracking.ui

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.cuongtd.cryptotracking.ui.theme.TickerListCompose
import com.cuongtd.cryptotracking.ui.tickers.TickerListHeader
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun MainScreenCompose(tickersViewModel: TickersViewModel, onNavigateOrderBook: (symbol: String) -> Unit) {
    val currentTab: Tabs? by tickersViewModel.currentTab.observeAsState()

    Scaffold(
        topBar = { AppTopBar() }
        ) {
        Column {
            AppTabBar(tickersViewModel)
            TickerListHeader(tickersViewModel)
            Crossfade(targetState = currentTab) {
                TickerListCompose(tickersViewModel, onNavigateOrderBook)
            }
        }
    }
}