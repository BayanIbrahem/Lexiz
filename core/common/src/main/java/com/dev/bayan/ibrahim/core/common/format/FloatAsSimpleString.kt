package com.dev.bayan.ibrahim.core.common.format

import java.text.DecimalFormat


fun Float.asSimpleString(
    floatingPoint: Int = 1,
): String {
    return DecimalFormat(
        "0." + "#".repeat(maxOf(0, floatingPoint))
    ).format(this)
}