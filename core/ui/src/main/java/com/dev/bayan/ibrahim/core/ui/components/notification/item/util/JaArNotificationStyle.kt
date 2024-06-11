package com.dev.bayan.ibrahim.core.ui.components.notification.item.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * the color style of the notification
 * - if normal use [TERTIARY] or [TERTIARY_CONTAINER]
 * - if urgent use [ERROR] or [ERROR_CONTAINER]
 * @property content content color of the current style (like [MaterialTheme.colorScheme.onError] for [ERROR] style)
 * @property container container color of the current style (like [MaterialTheme.colorScheme.Error] for [ERROR] style)
 */
enum class JaArNotificationStyle {
    TERTIARY,
    TERTIARY_CONTAINER,
    ERROR,
    ERROR_CONTAINER;

    val content: Color
        @Composable
        get() = when (this) {
            TERTIARY -> MaterialTheme.colorScheme.onTertiary
            TERTIARY_CONTAINER -> MaterialTheme.colorScheme.onTertiaryContainer
            ERROR -> MaterialTheme.colorScheme.onError
            ERROR_CONTAINER -> MaterialTheme.colorScheme.onErrorContainer
        }
    val container: Color
        @Composable
        get() = when (this) {
            TERTIARY -> MaterialTheme.colorScheme.tertiary
            TERTIARY_CONTAINER -> MaterialTheme.colorScheme.tertiaryContainer
            ERROR -> MaterialTheme.colorScheme.error
            ERROR_CONTAINER -> MaterialTheme.colorScheme.errorContainer
        }
}