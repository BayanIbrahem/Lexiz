package com.dev.bayan.ibrahim.core.common.range

import kotlin.math.max

infix fun IntRange.extend(to: Int): IntRange = if (to > last) first..to else this

infix fun ClosedFloatingPointRange<Float>.extend(
    to: Float
): ClosedFloatingPointRange<Float> = start..max(endInclusive, to)

infix fun ClosedFloatingPointRange<Float>.percentExtend(
    percent: Float
): ClosedFloatingPointRange<Float> = start..(endInclusive * (1 + percent))

fun ClosedFloatingPointRange<Float>.stepsExtends(
    currentSteps: Int,
    extraRequiredSteps: Int,
): ClosedFloatingPointRange<Float> = percentExtend(
    percent = extraRequiredSteps.toFloat() / (currentSteps + extraRequiredSteps)
)
