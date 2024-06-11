package com.dev.bayan.ibrahim.core.ui.components.notification.item.util

/**
 * duration of the floating notification before automatic dismiss
 * @property millis duration of milli seconds
 */
enum class JaArNotificationDuration(val millis: Long) {
    PERMANENT(Long.MAX_VALUE),
    SHORT(4_000L),
    NORMAL(7_000L),
    LONG(10_000L),
}
