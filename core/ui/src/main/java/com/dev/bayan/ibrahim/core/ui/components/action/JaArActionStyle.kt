package com.dev.bayan.ibrahim.core.ui.components.action

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Possible values:
 * - [PRIMARY]
 * - [SECONDARY]
 * - [TERTIARY]
 * - [SURFACE]
 * - [ERROR]
 * @property color main color of type like [MaterialTheme.colorScheme.primary] for [PRIMARY]
 * @property onColor color on [color] of type like [MaterialTheme.colorScheme.onPrimary] for [PRIMARY]
 */
enum class JaArActionStyle {
    PRIMARY, PRIMARY_CONTAINER,
    SECONDARY, SECONDARY_CONTAINER,
    TERTIARY, TERTIARY_CONTAINER,
    SURFACE, SURFACE_CONTAINER,
    ERROR, ERROR_CONTAINER;

    val color: Color
        @Composable
        get() = when (this) {
            PRIMARY -> MaterialTheme.colorScheme.primary
            SECONDARY -> MaterialTheme.colorScheme.secondary
            TERTIARY -> MaterialTheme.colorScheme.tertiary
            SURFACE -> MaterialTheme.colorScheme.surface
            ERROR -> MaterialTheme.colorScheme.error
            PRIMARY_CONTAINER -> MaterialTheme.colorScheme.primaryContainer
            SECONDARY_CONTAINER -> MaterialTheme.colorScheme.secondaryContainer
            TERTIARY_CONTAINER -> MaterialTheme.colorScheme.tertiaryContainer
            SURFACE_CONTAINER -> MaterialTheme.colorScheme.surfaceContainer
            ERROR_CONTAINER -> MaterialTheme.colorScheme.errorContainer
        }
    val onColor: Color
        @Composable
        get() = when (this) {
            PRIMARY -> MaterialTheme.colorScheme.onPrimary
            SECONDARY -> MaterialTheme.colorScheme.onSecondary
            TERTIARY -> MaterialTheme.colorScheme.onTertiary
            SURFACE -> MaterialTheme.colorScheme.onSurface
            ERROR -> MaterialTheme.colorScheme.onError
            PRIMARY_CONTAINER -> MaterialTheme.colorScheme.onPrimaryContainer
            SECONDARY_CONTAINER -> MaterialTheme.colorScheme.onSecondaryContainer
            TERTIARY_CONTAINER -> MaterialTheme.colorScheme.onTertiaryContainer
            SURFACE_CONTAINER -> MaterialTheme.colorScheme.onSurface
            ERROR_CONTAINER -> MaterialTheme.colorScheme.onErrorContainer
        }

}
