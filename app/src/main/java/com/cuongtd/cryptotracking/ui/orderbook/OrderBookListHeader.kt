package com.cuongtd.cryptotracking.ui.orderbook

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderBookListHeader() {
    Column() {
//    Column(Modifier.background(MaterialTheme.colors.primaryVariant)) {
//        Text(
//            "Order Book",
//            fontSize = 18.sp,
//            color = MaterialTheme.colors.onBackground,
//            fontWeight = FontWeight.W600,
//            modifier = Modifier.padding(start = 15.dp, bottom = 0.dp),
//            fontFamily = FontFamily.SansSerif
//        )
        Row(
            Modifier
                .height(25.dp)
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Bids",
                    fontSize = 10.sp,
                    color = MaterialTheme.colors.onSecondary,

                    textAlign = TextAlign.Start
                )
                Divider(
                    modifier = Modifier.padding(top = 3.dp, end = 2.dp),
                    color = Color(0xFF50555E).copy(0.8f),
                    thickness = 0.2.dp
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f).padding(start = 2.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Asks",
                    fontSize = 10.sp,
                    color = MaterialTheme.colors.onSecondary,
                    textAlign = TextAlign.Start
                )
                Divider(
                    modifier = Modifier.padding(top = 3.dp),
                    color = Color(0xFF50555E).copy(0.8f),
                    thickness = 0.2.dp
                )
            }
        }
    }
}