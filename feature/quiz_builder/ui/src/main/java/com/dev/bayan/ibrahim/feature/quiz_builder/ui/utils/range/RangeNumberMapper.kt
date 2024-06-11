package com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.range

import kotlin.math.roundToInt


fun ClosedFloatingPointRange<Float>.asIntRange(): IntRange {
    return (start.roundToInt()..endInclusive.roundToInt())
}

fun IntRange.asFloatRange(): ClosedFloatingPointRange<Float> {
    return first.toFloat()..last.toFloat()
}