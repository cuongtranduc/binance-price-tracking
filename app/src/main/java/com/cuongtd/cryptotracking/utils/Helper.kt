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
            return trimTrailingZero(price).toString()
        }

        fun formatPriceDouble(price: Double): String {
            return DecimalFormat("#,###.##").format(price.toDouble())
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
    }
}
