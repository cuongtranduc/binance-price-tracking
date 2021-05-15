package com.cuongtd.cryptotracking.utils

import java.text.DecimalFormat
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
            return price
        }
    }
}
