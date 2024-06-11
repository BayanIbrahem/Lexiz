package com.dev.bayan.ibrahim.core.common.range

fun Float.mapFromPercentageRange(
    to: ClosedFloatingPointRange<Float>
): Float = map(0f..100f, to)

fun Float.mapFromReversedPercentageRange(
    to: ClosedFloatingPointRange<Float>
): Float = map(100f, 0f, to.start, to.endInclusive)

fun Float.mapToPercentageRange(from: ClosedFloatingPointRange<Float>) = map(from, 0f..100f)
fun Float.mapToReversedPercentageRange(
    from: ClosedFloatingPointRange<Float>
) = map(from.start, from.endInclusive, 100f, 0f)

fun Int.mapFromPercentageRange(
    to: IntRange
): Int = map(0..100, to)

fun Int.mapFromReversedPercentageRange(
    to: IntRange,
): Int = map(100, 0, to.first, to.last)

fun Int.mapToPercentageRange(from: IntRange) = map(from, 0..100)
fun Int.mapToReversedPercentageRange(
    from: IntRange
) = map(from.first, from.last, 100, 0)
