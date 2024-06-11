package com.dev.bayan.ibrahim.feature.library.domain.util

internal fun String.toSuggestionRegex(ignorableChars: Set<Char> = setOf()): Regex {
    val regexPattern = this
        .trim()
        .lowercase()
        .toList()
        .filterNot { it in ignorableChars }
        .joinToString(".*", ".*", ".*")
    return Regex(regexPattern)
}