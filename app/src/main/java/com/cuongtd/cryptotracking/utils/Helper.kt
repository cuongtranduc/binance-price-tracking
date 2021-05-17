package com.cuongtd.cryptotracking.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

val df = DecimalFormat("#,###")

class Helper {
    companion object {
        fun formatVolume(volume: String): String {
            return df.format(volume.toDouble().roundToInt()).toString()
        }

        fun formatPercentageChange(change: String): String {
            val decimalFormat = DecimalFormat("#.##")
            val changeInDouble = change.toDouble()
            if (changeInDouble >= 0) {
                return "+${decimalFormat.format(Math.abs(changeInDouble))} %"
            }
            return "-${decimalFormat.format(Math.abs(changeInDouble))} %"
        }

        fun formatPrice(price: String): String {
            if (price.toDouble() > 1) {
                return DecimalFormat("#.##").format(price.toDouble())
            }
            return trimTrailingZero(price).toString()
        }

        fun formatPriceDouble(price: Double): String {
            return DecimalFormat("#,###.##").format(price.toDouble())
        }

        fun formatQuantity(quantity: String): String {
            return if (quantity.toDouble() >= 1 )
                DecimalFormat("#,###.###").format(quantity.toDouble()) else
                DecimalFormat("#.######").format(quantity.toDouble())
        }

        private fun trimTrailingZero(value: String?): String? {
            return if (!value.isNullOrEmpty()) {
                if (value!!.indexOf(".") < 0) {
                    value

                } else {
                    value.replace("0*$".toRegex(), "").replace("\\.$".toRegex(), "")
                }

            } else {
                value
            }
        }

        fun formatTradeTime(s: String): String {
            return try {
                val sdf = SimpleDateFormat("HH:mm:ss")
                val netDate = Date(s.toLong())
                sdf.format(netDate)
            } catch (e: Exception) {
                e.toString()
            }
        }
    }
}
