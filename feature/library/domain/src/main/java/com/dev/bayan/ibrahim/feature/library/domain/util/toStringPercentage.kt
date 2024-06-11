package com.dev.bayan.ibrahim.feature.library.domain.util

import kotlin.math.roundToInt

fun Float.toStringPercentage(
    minValue: Float = 0f,
    maxValue: Float = 1f,
): String {
    val max = maxOf(minValue, maxValue)
    val min = minOf(minValue, maxValue)

    if (this < min) return "0%"
    if (this > max) return "100%"

    val diff = max - min
    val percentage = if (this.isNaN()) 0 else ((this - min) * 100 / diff).roundToInt()
    return "$percentage%"
}
