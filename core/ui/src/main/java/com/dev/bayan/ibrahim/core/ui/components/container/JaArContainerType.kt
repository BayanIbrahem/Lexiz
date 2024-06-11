package com.dev.bayan.ibrahim.core.ui.components.container

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class JaArContainerType {
    PRIMARY,
    SECONDARY,
    SURFACE;

    val containerColor: Color
        @Composable
        get() = when (this) {
            PRIMARY -> MaterialTheme.colorScheme.primaryContainer
            SECONDARY -> MaterialTheme.colorScheme.secondaryContainer
            SURFACE -> MaterialTheme.colorScheme.surface
        }
    val onContainerColor: Color
        @Composable
        get() = when (this) {
            PRIMARY -> MaterialTheme.colorScheme.onPrimaryContainer
            SECONDARY -> MaterialTheme.colorScheme.onSecondaryContainer
            SURFACE -> MaterialTheme.colorScheme.onSurface
        }
}