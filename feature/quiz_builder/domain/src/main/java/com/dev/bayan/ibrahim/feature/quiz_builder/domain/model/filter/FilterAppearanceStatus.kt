package com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter

enum class FilterAppearanceStatus {
    SELECTED,
    NORMAL,
    UNAVAILABLE;

    val alpha: Float
        get() = when (this) {
            SELECTED -> 1f
            NORMAL -> 1f
            UNAVAILABLE -> 0.38f
        }
    val clickable: Boolean get() = this != UNAVAILABLE
}