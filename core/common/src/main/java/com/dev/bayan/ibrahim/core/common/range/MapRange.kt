package com.dev.bayan.ibrahim.core.common.range

fun Int.map(
    from: IntRange,
    to: IntRange,
) = map(from.first, from.last, to.first, to.last)

fun Float.map(
    from: ClosedFloatingPointRange<Float>,
    to: ClosedFloatingPointRange<Float>
) = map(from.start, from.endInclusive, to.start, to.endInclusive)

fun Int.map(
    fromStart: Int,
    fromEnd: Int,
    toStart: Int,
    toEnd: Int,
): Int {
    val m = slope(fromStart, fromEnd, toStart, toEnd)
    val p = toStart - (fromStart * m)
    return this * m + p
}

fun Float.map(
    fromStart: Float,
    fromEnd: Float,
    toStart: Float,
    toEnd: Float,
): Float {
    val m = slope(fromStart, fromEnd, toStart, toEnd)
    val p = toStart - (fromStart * m)
    return this * m + p
}


fun slope(
    inputStart: Float,
    inputEnd: Float,
    outputStart: Float,
    outputEnd: Float,
): Float = (outputEnd - outputStart) / (inputEnd - inputStart)

fun slope(
    inputStart: Int,
    inputEnd: Int,
    outputStart: Int,
    outputEnd: Int,
): Int = (outputEnd - outputStart) / (inputEnd - inputStart)
