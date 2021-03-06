package com.cuongtd.cryptotracking

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.navigation.compose.*
import com.cuongtd.cryptotracking.ui.EnterTransition
import com.cuongtd.cryptotracking.ui.MainScreenCompose
import com.cuongtd.cryptotracking.ui.OrderBookScreenCompose
import com.cuongtd.cryptotracking.ui.theme.CryptoTrackingTheme
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel
import java.util.*

class MainActivity : ComponentActivity() {
    private val tickersViewModel by viewModels<TickersViewModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            fun onNavigateOrderBook(symbol: String, ticker: String) {
                navController.navigate("order/${symbol}/${ticker}")
            }

            CryptoTrackingTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            MainScreenCompose(tickersViewModel, ::onNavigateOrderBook)
                        }
                        composable(
                            "order/{symbol}/{ticker}",
                            arguments = listOf(
                                navArgument("symbol") { defaultValue = "BTCUSDT" },
                                navArgument("ticker") { defaultValue = "" }
                            )
                        ) {
                            fun goBack() {
                                navController.popBackStack()
                            }
                            EnterTransition {
                                OrderBookScreenCompose(
                                    tickersViewModel,
                                    symbol = it.arguments?.getString("symbol"),
                                    tickerJson = it.arguments?.getString("ticker"),
                                    goBack = ::goBack
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}