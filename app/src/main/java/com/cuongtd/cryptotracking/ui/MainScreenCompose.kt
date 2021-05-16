package com.cuongtd.cryptotracking.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.cuongtd.cryptotracking.ui.theme.TickerListCompose
import com.cuongtd.cryptotracking.ui.theme.TickerListHeader
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun MainScreenCompose(tickersViewModel: TickersViewModel) {
    val currentTab: Tabs? by tickersViewModel.currentTab.observeAsState()

    Scaffold(
        topBar = { AppTopBar() }
        ) {
        Column {
            AppTabBar(tickersViewModel)
            TickerListHeader(tickersViewModel)
            Crossfade(targetState = currentTab) {
                TickerListCompose(tickersViewModel)
            }
        }
    }
}