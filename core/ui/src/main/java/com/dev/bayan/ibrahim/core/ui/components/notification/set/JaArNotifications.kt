package com.dev.bayan.ibrahim.core.ui.components.notification.set

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NewReleases
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.components.notification.item.JaArNotificationHost
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.JaArNotificationDuration
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.JaArNotificationHostState
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.JaArNotificationStyle
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.rememberJaArNotificationHostState
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeExpandAnimatedVisibilityEnterTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeShrinkAnimatedVisibilityExitTransition
import kotlinx.coroutines.delay
import kotlin.random.Random

private val outer_padding = 8.dp
private val outer_radius = 8.dp

private val corner = 8.dp
private val expanded_corner = 8.dp

private val padding = 0.dp
private val expanded_padding = 4.dp

private val clickable_size = 24.dp
private val expanded_clickable_size = 48.dp

private val vector_size = 16.dp
private val expanded_vector_size = 24.dp

private val badge_size = 8.dp

@Composable
fun JaArNotifications(
    modifier: Modifier = Modifier,
    state: JaArNotificationHostState,
    duration: Int = 100,
    onDismissNotification: (key: Long) -> Unit = {},
    placementAnimation: FiniteAnimationSpec<IntOffset> = tween(200),
    visibilityDuration: Int = 1000,
    boxDuration: Int = 100,
    maxListHeight: Dp = 300.dp,
) {
    val hasNotifications = state.currentNotifications.any { !it.dismissRequest }
    val hasNormalNotifications = state.currentNotifications.any {
        !it.dismissRequest && (it.style == JaArNotificationStyle.TERTIARY
                || it.style == JaArNotificationStyle.TERTIARY_CONTAINER)
    }
    val hasImportantNotifications = state.currentNotifications.any {
        !it.dismissRequest && (it.style == JaArNotificationStyle.ERROR
                || it.style == JaArNotificationStyle.ERROR_CONTAINER)
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = hasNotifications) {
        expanded = false
        state.toggleIdle(true)
    }

    AnimatedVisibility(
        visible = hasNotifications,
        enter = fadeExpandAnimatedVisibilityEnterTransition(
            duration = duration,
            expandFrom = Alignment.TopEnd
        ),
        exit = fadeShrinkAnimatedVisibilityExitTransition(
            duration = duration,
            shrinkTowards = Alignment.TopEnd
        ),
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.End,
        ) {
            NotificationsIcon(
                expanded = expanded,
                hasImportant = hasImportantNotifications,
                hasNormal = hasNormalNotifications,
                duration = duration,
                onClick = {
                    state.toggleIdle(expanded)
                    expanded = !expanded
                }
            )
            NotificationsList(
                expanded = expanded,
                state = state,
                onDismissNotification = onDismissNotification,
                placementAnimation = placementAnimation,
                visibilityDuration = visibilityDuration,
                boxDuration = boxDuration,
                maxListHeight = maxListHeight
            )
        }
    }
}

@Composable
private fun NotificationsList(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    state: JaArNotificationHostState,
    onDismissNotification: (key: Long) -> Unit = {},
    placementAnimation: FiniteAnimationSpec<IntOffset> = tween(200),
    visibilityDuration: Int = 1000,
    boxDuration: Int = 100,
    maxListHeight: Dp = 300.dp,
) {
    val background = MaterialTheme.colorScheme.background
    val height by animateDpAsState(
        targetValue = if (expanded) maxListHeight else 0.dp,
        animationSpec = tween(durationMillis = boxDuration, easing = LinearEasing)
    )
    val cornerSize by animateDpAsState(
        targetValue = if (expanded) outer_radius else 0.dp,
        animationSpec = tween(boxDuration, easing = LinearEasing)
    )
    Box(
        modifier = modifier
            .wrapContentWidth()
            .height(height)
            .drawBehind {
                drawRoundRect(
                    color = background,
                    cornerRadius = CornerRadius(outer_radius.toPx()),
                    size = size.copy(width = size.width + cornerSize.toPx())
                )
                drawOuterCorner(0, background, cornerSize, Offset(size.width, size.height))
            }
            .padding(outer_padding)
    ) {
        JaArNotificationHost(
            modifier = Modifier.wrapContentWidth(),
            state = state,
            onDismissNotification = onDismissNotification,
            placementAnimation = placementAnimation,
            visibilityDuration = visibilityDuration,
        )
    }
}

@Composable
private fun NotificationsIcon(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    hasImportant: Boolean,
    hasNormal: Boolean,
    duration: Int = 100,
    onClick: () -> Unit,
) {
    val cornerSize by animateDpAsState(
        targetValue = if (expanded) expanded_corner else corner,
        animationSpec = tween(duration, easing = LinearEasing)
    )
    val outerCornerSize by animateDpAsState(
        targetValue = if (expanded) 0.dp else 8.dp,
        animationSpec = tween(duration, easing = LinearEasing)
    )
    val paddingValue by animateDpAsState(
        targetValue = if (expanded) expanded_padding else padding,
        animationSpec = tween(duration, easing = LinearEasing)
    )
    val clickableSize by animateDpAsState(
        targetValue = if (expanded) expanded_clickable_size else clickable_size,
        animationSpec = tween(duration, easing = LinearEasing)
    )
    val vectorSize by animateDpAsState(
        targetValue = if (expanded) expanded_vector_size else vector_size,
        animationSpec = tween(duration, easing = LinearEasing)
    )

    val normalBadgeOpacity by animateFloatAsState(
        targetValue = if (!expanded && hasNormal) 1f else 0f,
        animationSpec = tween(duration, easing = LinearEasing)
    )

    val importantBadgeOpacity by animateFloatAsState(
        targetValue = if (!expanded && hasImportant) 1f else 0f,
        animationSpec = tween(duration, easing = LinearEasing)
    )

    val normalBadgeTranslationX by animateDpAsState(
        targetValue = if (hasImportant) badge_size else badge_size / 2,
        animationSpec = tween(duration, easing = LinearEasing)
    )

    val background = MaterialTheme.colorScheme.background
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val tertiary = MaterialTheme.colorScheme.tertiary
    val error = MaterialTheme.colorScheme.error

    Box(
        modifier = modifier
            .drawBehind {
                // top left
                drawOuterCorner(0, background, outer_radius, Offset.Zero)
                // bottom right
                drawOuterCorner(0, background, outer_radius, Offset(size.width, size.height))
                // bottom left
                drawOuterCorner(
                    3,
                    background,
                    outer_radius - outerCornerSize,
                    Offset(0f, size.height)
                )
                drawRoundRect(
                    color = background,
                    cornerRadius = CornerRadius(outerCornerSize.toPx()),
                    topLeft = Offset(0f, -outerCornerSize.toPx()),
                    size = Size(
                        width = size.width + outerCornerSize.toPx(),
                        height = size.height + outerCornerSize.toPx(),
                    )
                )
            }
            .padding(outer_padding)
            .drawWithContent {
                drawRoundRect(
                    color = primaryContainer,
                    cornerRadius = CornerRadius(cornerSize.toPx()),
                )
                drawContent()
                drawCircle(
                    color = tertiary,
                    alpha = normalBadgeOpacity,
                    center = Offset(
                        x = normalBadgeTranslationX.toPx() + paddingValue.toPx(),
                        y = badge_size.toPx() / 2 + paddingValue.toPx()
                    ),
                    radius = badge_size.toPx() / 2,
                )
                drawCircle(
                    color = error,
                    alpha = importantBadgeOpacity,
                    center = Offset(
                        x = badge_size.toPx() / 2 + paddingValue.toPx(),
                        y = badge_size.toPx() / 2 + paddingValue.toPx()
                    ),
                    radius = badge_size.toPx() / 2,
                )
            }
            .padding(paddingValue)
    ) {
        IconButton(
            modifier = Modifier
                .size(clickableSize),
            onClick = onClick,
        ) {
            Icon(
                modifier = Modifier.size(vectorSize),
                imageVector = Icons.Outlined.NewReleases,
                contentDescription = stringResource(R.string.notifications),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

/**
 * draw rounded corner (reversed)
 * @param index pass number between 0 and 3
 * - 0: first quarter
 * - 1: second quarter
 * - 2: third quarter
 * - 3: forth quarter
 *
 */
fun DrawScope.drawOuterCorner(
    index: Int,
    color: Color,
    size: Dp,
    offset: Offset,
) {
    val order = index.coerceIn(0..3)
    val sizePx = size.toPx()
    val path = Path()
    // 0: -x | +Y | 0-90   | -x +y
    // 1: +x | +Y | 180+90 | +x +y
    // 2: +x | -Y | 180-90 | +x -y
    // 3: -x | -Y | 0+90   | -x -y
    val arcY = offset.y + if (order == 0 || order == 1) sizePx else -sizePx
    val arcStart = if (order == 0 || order == 3) 0f else 180f
    val arcSweep = if (order == 0 || order == 2) -90f else 90f
    val arcCenter = when (order) {
        0 -> offset + Offset(-sizePx, sizePx)
        1 -> offset + Offset(sizePx, sizePx)
        2 -> offset + Offset(sizePx, -sizePx)
        else -> offset + Offset(-sizePx, -sizePx)
    }
    path.moveTo(offset.x, arcY)

    path.addArc(
        oval = Rect(
            center = arcCenter,
            radius = sizePx
        ),
        startAngleDegrees = arcStart,
        sweepAngleDegrees = arcSweep,
    )
    path.lineTo(offset.x, offset.y)
    path.close()
    drawPath(
        path = path,
        color = color,
        style = Fill,
    )
}

@Preview(showBackground = false, apiLevel = 33)
@Composable
private fun IconPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
    ) {
        var expanded by remember {
            mutableStateOf(false)
        }
        var hasNormal by remember {
            mutableStateOf(false)
        }
        var hasImportant by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = Unit) {
            while (true) {
                delay(3000)
                hasNormal = true
                delay(3000)
                hasImportant = true
                delay(3000)
                hasImportant = false
                delay(3000)
                hasNormal = false
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            NotificationsIcon(
                expanded = expanded,
                hasNormal = hasNormal,
                hasImportant = hasImportant,
            ) {
                expanded = !expanded
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun JaArNotificationsPreview() {
    val state = rememberJaArNotificationHostState(10)
    var index by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(2_000 * Random.nextLong(1, 3))
            state.showMessage(
                label = "message #$index",
                duration = JaArNotificationDuration.entries.random(),
                style = JaArNotificationStyle.entries.random()
            )
            index = index.inc()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawRect(Color.Green)
                    drawOuterCorner(0, Color.Black, 20.dp, size.center)
                    drawOuterCorner(1, Color.Blue, 20.dp, size.center)
                    drawOuterCorner(2, Color.Yellow, 20.dp, size.center)
                    drawOuterCorner(3, Color.LightGray, 20.dp, size.center)
                },
            contentAlignment = Alignment.TopEnd
        ) {
            JaArNotifications(state = state)
        }
    }
}