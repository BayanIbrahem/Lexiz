package com.dev.bayan.ibrahim.core.common.range

fun IntRange.contains(that: IntRange): Boolean {
    return that.first >= first && that.last <= last
}