package com.cuongtd.cryptotracking.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cuongtd.cryptotracking.ui.theme.GainColor
import com.cuongtd.cryptotracking.ui.theme.LoseColor

@Composable
fun AppTopBar() {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        title = {
            Text(
                "Markets",
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSecondary,
                fontWeight = FontWeight.W600,
            )
        },
        actions = {
            IconButton(
                onClick = {
//                    coroutineScope.launch {
//                    }
                },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.onSecondary
                )
            }
        },
    )
}