package com.dev.bayan.ibrahim.core.ui.components.notification.item.util

import androidx.annotation.IntRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.null_coerce.coercedInOrNull
import com.dev.bayan.ibrahim.core.common.tuples.tof
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.JaArNotificationStyle.*
import com.qubit_team.apps.dental_clinic.core.common.tuples.Quadruple
import kotlinx.coroutines.delay

/**
 * host state of the floating notification
 * use it in compose scope using [rememberJaArNotificationHostState]
 * @param maxVisibleItemsCount max count of visible items
 * @param addAtTop if true then the new notifications will added to top of the list
 * @param initialNotifications used just if you want to add initial notifications list
 * @property currentNotifications current notification list (first one is at the top)
 *
 * @see [showMessage]
 * @see [animatedShowMessages]
 * @see [buildMessage]
 * @see [animateDismissMessage]
 * @see [dismissMessage]
 * @see [animateDismissAll]
 * @see [animateDelayedDismissAll]
 *
 */
@Stable
class JaArNotificationHostState(
    @IntRange(from = 1)
    val maxVisibleItemsCount: Int,
    val addAtTop: Boolean = false,
    initialNotifications: List<JaArNotificationVisuals> = listOf(),
    initNextKey: Long = 0,
) {
    private var nextKey: Long = initNextKey
        get() {
            return field.also {
                field = field.inc()
            }
        }
    val lastKey: Long get() = nextKey
    private val _currentNotifications = initialNotifications.map {
        VisualsImpl(it, nextKey).also {
            nextKey = nextKey.inc()
        }
    }.toMutableStateList()
    val currentNotifications: List<JaArNotificationVisuals> = _currentNotifications
    var idle: Boolean by mutableStateOf(false)
        private set

    /**
     * add a new message to the top/end of the list according to [addAtTop]  existed message with
     * the same id will be removed and then add the new message, if the list if fill then the oldest message
     * will be removed
     */
    fun showMessage(
        label: String,
        duration: JaArNotificationDuration = JaArNotificationDuration.SHORT,
        style: JaArNotificationStyle = TERTIARY,
        leadingIcon: JaArDynamicVector? = null,
        leadingIconAction: (() -> Unit)? = null,
        trailingIcon: JaArDynamicVector? = null,
        trailingIconAction: (() -> Unit)? = null,
        textMaxWidth: Dp = 150.dp,
    ): Long = showMessage(
        visuals = VisualsImpl(
            key = nextKey,
            label = label,
            duration = duration,
            style = style,
            leadingIcon = leadingIcon,
            leadingIconAction = leadingIconAction,
            trailingIcon = trailingIcon,
            trailingIconAction = trailingIconAction,
            textMaxWidth = textMaxWidth
        ).also {
            it.idle = idle
        }
    )

    /**
     * add a new message to the top/end of the list according to [addAtTop]  existed message with
     * the same id will be removed and then add the new message, if the list if fill then the oldest message
     * will be removed
     */
    private fun showMessage(
        visuals: JaArNotificationVisuals,
    ): Long {
        currentNotifications.indexOfFirst {
            it.key() == visuals.key()
        }.coercedInOrNull(currentNotifications)?.let {
            _currentNotifications.removeAt(it).run {
                if (addAtTop) {
                    _currentNotifications.add(0, this)
                } else {
                    _currentNotifications.add(this)
                }
            }
            return visuals.key()
        }
        while (
            currentNotifications.count { !it.dismissRequest } >= maxVisibleItemsCount
            && currentNotifications.isNotEmpty()
        ) {
            (if (addAtTop) {
                _currentNotifications.last { !it.dismissRequest }
            } else {
                _currentNotifications.first { !it.dismissRequest }
            }).dismissRequest = true
        }
        if (addAtTop) {
            _currentNotifications.add(0, VisualsImpl(visuals))
        } else {
            _currentNotifications.add(VisualsImpl(visuals))
        }
        return visuals.key()
    }

    /**
     * show a set of messages together
     */
    suspend fun animatedShowMessages(
        messages: List<JaArNotificationVisuals>,
        delay: Long,
    ) {
        messages.filterNot { it.dismissRequest }.forEach {
            it.let { notification ->
                showMessage(
                    label = notification.label,
                    duration = notification.duration,
                    style = notification.style,
                    leadingIcon = notification.leadingIcon,
                    leadingIconAction = notification.leadingIconAction,
                    trailingIcon = notification.trailingIcon,
                    trailingIconAction = notification.trailingIconAction,
                    textMaxWidth = notification.textMaxWidth
                )
            }
            delay(delay)
        }
    }

    /**
     * build a new message, used to prepare a set of messages then animate viewing them using
     * [animatedShowMessages]
     */
    fun buildMessage(
        label: String,
        duration: JaArNotificationDuration = JaArNotificationDuration.SHORT,
        style: JaArNotificationStyle = TERTIARY,
        leadingIcon: JaArDynamicVector? = null,
        leadingIconAction: (() -> Unit)? = null,
        trailingIcon: JaArDynamicVector? = null,
        trailingIconAction: (() -> Unit)? = null,
        textMaxWidth: Dp = 150.dp,
    ): JaArNotificationVisuals = VisualsImpl(
        key = nextKey,
        label = label,
        duration = duration,
        style = style,
        leadingIcon = leadingIcon,
        leadingIconAction = leadingIconAction,
        trailingIcon = trailingIcon,
        trailingIconAction = trailingIconAction,
        textMaxWidth = textMaxWidth
    )

    /**
     * remove any message according to its key, suggested to be used instead of [dismissMessage]
     */
    fun animateDismissMessage(key: Long) {
        _currentNotifications.forEach {
            if (it.key() == key) {
                it.dismissRequest = true
            }
        }
    }

    /**
     * use [animateDismissMessage] instead, this function instantly remove the notification
     */
    fun dismissMessage(key: Long) {
        _currentNotifications.removeAll {
            it.key() == key
        }
    }

    /**
     * use [animateDismissAll] instead, this function instantly remove all the notification
     */
    fun dismissAll() {
        _currentNotifications.clear()
    }

    /**
     * dismiss all messages together see [animateDelayedDismissAll]
     */
    fun animateDismissAll() {
        _currentNotifications.forEach {
            it.dismissRequest = true
        }
    }

    suspend fun animateDelayedDismissAll(delay: Long) {
        _currentNotifications.toList().forEach {
            it.dismissRequest = true
            delay(delay)
        }
    }

    /**
     * redisplay the init animation of the message
     */
    suspend fun animateRedisplay(delay: Long) {
        val notifications = currentNotifications.toList()
        dismissAll()
        animatedShowMessages(notifications, delay)
    }

    fun toggleIdle(idle: Boolean) {
        this.idle = idle
        _currentNotifications.toList().forEach {
            it.idle = idle
        }
    }

    private data class VisualsImpl(
        override val label: String,
        val key: Long,
        override val duration: JaArNotificationDuration,
        override val style: JaArNotificationStyle,
        override val leadingIcon: JaArDynamicVector?,
        override val leadingIconAction: (() -> Unit)?,
        override val trailingIcon: JaArDynamicVector?,
        override val trailingIconAction: (() -> Unit)?,
        override val textMaxWidth: Dp,
    ) : JaArNotificationVisuals {
        constructor(
            visual: JaArNotificationVisuals,
            key: Long = visual.key(),
        ) : this(
            label = visual.label,
            key = key,
            duration = visual.duration,
            style = visual.style,
            leadingIcon = visual.leadingIcon,
            leadingIconAction = visual.leadingIconAction,
            trailingIcon = visual.trailingIcon,
            trailingIconAction = visual.trailingIconAction,
            textMaxWidth = visual.textMaxWidth
        )

        override fun key() = key
        override var idle: Boolean by mutableStateOf(false)
        override var dismissRequest: Boolean by mutableStateOf(false)

    }
}


@Composable
fun rememberJaArNotificationHostState(
    @IntRange(from = 1)
    maxVisibleItemsCount: Int = 5,
    addAtTop: Boolean = false,
    initialNotifications: List<JaArNotificationVisuals> = listOf(),
) = rememberSaveable(
    saver = JaArNotificationHostStateSaver()
) {
    JaArNotificationHostState(
        maxVisibleItemsCount = maxVisibleItemsCount,
        addAtTop = addAtTop,
        initialNotifications = initialNotifications
    )
}

private class JaArNotificationHostStateSaver :
    Saver<JaArNotificationHostState, Any> {
    @Suppress("UNCHECKED_CAST")
    override fun restore(value: Any): JaArNotificationHostState {
        val info = value as Quadruple<Int, Boolean, List<JaArNotificationVisuals>, Long>
        return JaArNotificationHostState(
            maxVisibleItemsCount = info.first,
            addAtTop = info.second,
            initialNotifications = info.third,
            initNextKey = info.forth
        )
    }

    override fun SaverScope.save(
        value: JaArNotificationHostState,
    ): Any {
        return value.maxVisibleItemsCount to
                value.addAtTop to
                value.currentNotifications tof
                value.lastKey
    }
}