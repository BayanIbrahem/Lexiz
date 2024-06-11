package com.dev.bayan.ibrahim.core.ui.components.container

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class JaArFabType(
    val height: Dp,
    val minWidth: Dp,
    val maxWidth: Dp,
    val cornerRadius: Dp,
    val iconSize: Dp,
    val padding: Dp,
) {
    SMALL(
        height = 40.dp,
        minWidth = 40.dp,
        maxWidth = 40.dp,
        cornerRadius = 12.dp,
        iconSize = 24.dp,
        padding = 12.dp
    ),
    NORMAL(
        height = 56.dp,
        minWidth = 56.dp,
        maxWidth = 56.dp,
        cornerRadius = 16.dp,
        iconSize = 24.dp,
        padding = 16.dp
    ),
    LARGE(
        height = 96.dp,
        minWidth = 96.dp,
        maxWidth = 96.dp,
        cornerRadius = 28.dp,
        iconSize = 36.dp,
        padding = 16.dp
    ),
    EXTENDED(
        height = 56.dp,
        minWidth = 80.dp,
        maxWidth = 120.dp,
        cornerRadius = 16.dp,
        iconSize = 24.dp,
        padding = 16.dp
    ),
}