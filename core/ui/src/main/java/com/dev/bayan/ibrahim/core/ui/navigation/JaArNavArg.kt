package com.dev.bayan.ibrahim.core.ui.navigation

data class JaArNavArg(
    val label: String,
    val value: String?,
)

fun Collection<String>.asNavArg(): Set<JaArNavArg> =
    this.map { JaArNavArg(it, "{$it}") }.toSet()

fun <T : Enum<T>> Collection<T>.asNavLabels(): Set<String> = this.map { it.name }.toSet()
