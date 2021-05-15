package com.cuongtd.cryptotracking.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cuongtd.cryptotracking.utils.Constants

enum class Tabs(val symbol: String) {
    BTC(Constants.BTC), ETH(Constants.ETH), BNB(Constants.BNB), USDT(Constants.USDT),
}

@Composable
fun AppTabBar(currentTab: Tabs, onChangeTab: (Tabs) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(Modifier.background(MaterialTheme.colors.primaryVariant)) {
        Tabs.values().forEach { tab ->
            Row(
                Modifier
                    .weight(1f, true)
                    .height(30.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onChangeTab(tab)
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        tab.name,
                        color = if (tab == currentTab) Color(0xFFF0B90B) else MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight(600)
                    )
                    if (tab == currentTab) {
                        Divider(
                            Modifier.padding(top = 2.dp),
                            color = MaterialTheme.colors.primary,
                            thickness = 2.dp
                        )
                    }
                }
            }
        }
    }
}