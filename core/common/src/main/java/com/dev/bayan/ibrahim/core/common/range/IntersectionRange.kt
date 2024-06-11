package com.dev.bayan.ibrahim.core.common.range


infix fun IntRange?.intersect(other: IntRange?): IntRange? {
    this ?: return null
    other ?: return null

    val maxFirst = maxOf(first, other.first)
    val minLast = minOf(last, other.last)

    if (maxFirst > minLast) return null

    return maxFirst..minLast
}
