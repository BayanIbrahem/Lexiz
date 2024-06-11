package com.dev.bayan.ibrahim.core.ui.components.notification.item

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.JaArNotificationDuration
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.JaArNotificationHostState
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.JaArNotificationStyle
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.rememberJaArNotificationHostState
import kotlinx.coroutines.launch
import kotlin.random.Random

private val CONTENT_PADDING = 8.dp
private val SPACED_BY = 8.dp
private fun height(
    count: Int,
): Dp =
    CONTENT_PADDING.times(2) + (SPACED_BY * count.dec()) + (JA_AR_FLOATING_NOTIFICATION_WIDTH * count)

@Composable
fun JaArNotificationHost(
    modifier: Modifier = Modifier,
    state: JaArNotificationHostState,
    onDismissNotification: (key: Long) -> Unit = {},
    placementAnimation: FiniteAnimationSpec<IntOffset> = tween(200),
    visibilityDuration: Int = 1000,
) {
    LazyColumn(
        modifier = modifier.heightIn(min = height(state.maxVisibleItemsCount)),
        verticalArrangement = Arrangement.spacedBy(SPACED_BY),
        horizontalAlignment = Alignment.End,
        contentPadding = PaddingValues(CONTENT_PADDING),
    ) {
        items(
            items = state.currentNotifications,
            key = { it.key() }
        ) {
            JaArNotification(
                visuals = it,
                onDismiss = {
                    state.dismissMessage(it.key())
                    onDismissNotification(it.key())
                },
                placementAnimation = placementAnimation,
                visibilityDuration = visibilityDuration,
            )
        }
    }
}

@Preview(showBackground = false, device = "spec:width=400dp,height=600dp,dpi=440")
@Composable
fun JaArNotificationHostPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        var index by rememberSaveable { mutableIntStateOf(0) }
        val hostState = rememberJaArNotificationHostState(20)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            JaArNotificationHost(
                state = hostState,
            )
            val scope = rememberCoroutineScope()
            Button(
                modifier = Modifier.align(Alignment.BottomStart),
                onClick = {
                    scope.launch {
                        hostState.animateRedisplay(100)
                    }
                }
            ) {
                Text(text = "reshow")
            }
            Button(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = {
                    scope.launch {
                        hostState.animateDelayedDismissAll(100)
                    }
                }
            ) {
                Text(text = "dismiss")
            }
            Button(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = {
                    hostState.showMessage(
                        label = "message #$index",
                        style = JaArNotificationStyle.entries[Random.nextInt(0, 4)],
                        leadingIcon = if (Random.nextBoolean()) {
                            JaArDynamicVector.Vector(Icons.Outlined.Lightbulb)
                        } else null,
                        duration = JaArNotificationDuration.LONG
                    )
                    index = index.inc() % 20
                }
            ) {
                Text(text = "add")
            }
        }
    }
}