package com.dev.bayan.ibrahim.feature.library.domain.util

internal fun String.ignorabledLowercase(ignorableChars: Set<Char>): String {
    return lowercase().filterNot { it in ignorableChars }
}
