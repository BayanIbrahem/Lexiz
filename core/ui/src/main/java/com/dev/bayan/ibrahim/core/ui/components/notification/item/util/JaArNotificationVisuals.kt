package com.dev.bayan.ibrahim.core.ui.components.notification.item.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector


/**
 * interface of the floating notification item
 * implement this interface to your class to use it as floating notification item
 * @property label text of the notification it should be short
 * @property duration duration of the notification see [JaArNotificationDuration]
 * @property style style of the notification see [JaArNotificationStyle]
 * @property leadingIcon leading icon (optional) if [leadingIconAction] is not null this it will be
 * clickable
 * @property leadingIconAction callback on leading icon click (it will dismiss the notification)
 * @property trailingIcon trailing icon (optional) if [trailingIconAction] is not null this it will be
 * clickable
 * @property trailingIconAction callback on trailing icon click (it will dismiss the notification)
 * @property textMaxWidth max width of the text (it can get more that one value according to screen size
 * @property key function that return the key, the returned value is used to dismiss the notification
 * if you added a notification of an existed key then the old one would be replaced with the new one
 */
@Stable
interface JaArNotificationVisuals {
    val label: String
    val duration: JaArNotificationDuration
    val style: JaArNotificationStyle
    val leadingIcon: JaArDynamicVector?
    val leadingIconAction: (() -> Unit)?
    val trailingIcon: JaArDynamicVector?
    val trailingIconAction: (() -> Unit)?
    val textMaxWidth: Dp
    val dismissRequest: Boolean
    val idle: Boolean
    fun key(): Long
}

