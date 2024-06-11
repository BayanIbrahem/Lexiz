package com.dev.bayan.ibrahim.core.common

enum class JaArScreenSize {
    COMPAT,
    MEDIUM,
    EXPANDED,

    COMPAT_SHORT,
    MEDIUM_SHORT,
    EXPANDED_SHORT,

    COMPAT_HIGH,
    MEDIUM_HIGH,
    EXPANDED_HIGH,

    //    COMPAT_FOLD_VERTICAL,
    MEDIUM_FOLD_VERTICAL,
    EXPANDED_FOLD_VERTICAL,

    COMPAT_FOLD_HORIZONTAL,
    MEDIUM_FOLD_HORIZONTAL,
    EXPANDED_FOLD_HORIZONTAL;

    fun isCompat(): Boolean = this == COMPAT || this == COMPAT_SHORT
    fun isMedium(): Boolean = this == MEDIUM || this == MEDIUM_SHORT
    fun isExpanded(): Boolean = this == EXPANDED || this == EXPANDED_SHORT

    fun isShort(): Boolean = this == COMPAT_SHORT || this == MEDIUM_SHORT || this == EXPANDED_SHORT
    fun isExpandedHeight(): Boolean =
        this == COMPAT_HIGH || this == MEDIUM_HIGH || this == EXPANDED_HIGH

    fun isVerticalFold(): Boolean = /*this == COMPAT_FOLD_VERTICAL || */
        this == MEDIUM_FOLD_VERTICAL || this == EXPANDED_FOLD_VERTICAL

    fun isHorizontalFold(): Boolean =
        this == COMPAT_FOLD_HORIZONTAL || this == MEDIUM_FOLD_HORIZONTAL || this == EXPANDED_FOLD_HORIZONTAL

    fun isFold(): Boolean = isVerticalFold() || isHorizontalFold()
}
