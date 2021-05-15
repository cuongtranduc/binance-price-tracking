package com.cuongtd.cryptotracking.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import com.cuongtd.cryptotracking.ui.theme.TickerListCompose
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel

@Composable
fun MainScreenCompose(tickersViewModel: TickersViewModel) {
    var currentTab by remember { mutableStateOf(Tabs.BTC) }

    Scaffold(
        topBar = { AppTopBar() },
    ) {
        Column {
            AppTabBar(currentTab, onChangeTab = { tab -> currentTab = tab })
            Crossfade(targetState = currentTab) { it ->
                TickerListCompose(tickersViewModel, it)
            }
        }
    }
}