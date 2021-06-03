package com.cuongtd.cryptotracking.ui.orderbook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderBookTopBar(symbol: String, goBack: () -> Unit) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        navigationIcon = {
            IconButton(
                onClick = {
                    goBack()
                },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
        title = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    symbol,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Center
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.Equalizer,
                    null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
    )
}