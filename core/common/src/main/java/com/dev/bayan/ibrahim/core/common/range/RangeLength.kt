package com.dev.bayan.ibrahim.core.common.range

fun ClosedFloatingPointRange<Float>.len(): Float = endInclusive - start

fun IntRange.len() = last - first
