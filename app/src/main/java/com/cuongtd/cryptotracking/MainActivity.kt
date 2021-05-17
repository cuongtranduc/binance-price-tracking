package com.cuongtd.cryptotracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.navigation.compose.*
import com.cuongtd.cryptotracking.ui.MainScreenCompose
import com.cuongtd.cryptotracking.ui.OrderBookScreenCompose
import com.cuongtd.cryptotracking.ui.theme.CryptoTrackingTheme
import com.cuongtd.cryptotracking.viewmodels.TickersViewModel

class MainActivity : ComponentActivity() {
    private val tickersViewModel = TickersViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            fun onNavigateOrderBook(symbol: String) {
                navController.navigate("order/${symbol}")
            }

            CryptoTrackingTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { backStackEntry ->
                            MainScreenCompose(tickersViewModel, ::onNavigateOrderBook)
                        }
                        composable(
                            "order/{symbol}",
                            arguments = listOf(navArgument("symbol") { defaultValue = "BTCUSDT" })
                        ) {
                            fun goBack() {
                                navController.popBackStack()
                            }

                            OrderBookScreenCompose(symbol = it.arguments?.getString("symbol"), goBack = ::goBack)
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