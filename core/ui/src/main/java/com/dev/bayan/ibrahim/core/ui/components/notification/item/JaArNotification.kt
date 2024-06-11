package com.dev.bayan.ibrahim.core.ui.components.notification.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.JaArNotificationDuration
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.JaArNotificationDuration.*
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.JaArNotificationStyle
import com.dev.bayan.ibrahim.core.ui.components.notification.item.util.JaArNotificationVisuals
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val CORNER_SIZE = 8.dp
private val ICON_SIZE = 16.dp
private val ICON_BOX_SIZE = 32.dp
private val DEFAULT_MAX_TEXT_WIDTH = 150.dp
private val CONTENT_PADDING = 0.dp
val JA_AR_FLOATING_NOTIFICATION_WIDTH: Dp
    get() = ICON_BOX_SIZE + CONTENT_PADDING.times(2)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.JaArNotification(
    modifier: Modifier = Modifier,
    visuals: JaArNotificationVisuals,
    onDismiss: () -> Unit,
    placementAnimation: FiniteAnimationSpec<IntOffset>,
    visibilityDuration: Int,
) {
    var firstInit by rememberSaveable(true) {
        mutableStateOf(true)
    }
    var removing by remember(visuals.dismissRequest) { mutableStateOf(visuals.dismissRequest) }
    LaunchedEffect(key1 = Unit) {
        firstInit = false
    }

    val coroutineScope = rememberCoroutineScope()
    val onDismissRequest: () -> Unit by remember {
        mutableStateOf(
            {
                coroutineScope.launch {
                    removing = true
                    delay(visibilityDuration.toLong())
                    onDismiss()
                }
            }
        )
    }
    LaunchedEffect(key1 = removing) {
        if (removing) {
            onDismissRequest()
        }
    }
    var durationValue by rememberSaveable {
        mutableFloatStateOf(visuals.duration.millis.toFloat())
    }
    val durationAnimatable by remember(durationValue) {
        mutableStateOf(Animatable(durationValue))
    }
    LaunchedEffect(key1 = visuals.idle) {
        durationValue = durationAnimatable.value
        if (!visuals.idle) {
            durationAnimatable.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = durationValue.toInt(),
                    easing = LinearEasing
                )
            )
            onDismissRequest()
        }
    }
    DisposableEffect(
        key1 = Unit,
        effect = {
            onDispose {
                durationValue = durationAnimatable.value
                if (removing) {
                    onDismiss()
                }
            }
        }
    )
    AnimatedVisibility(
        modifier = Modifier.animateItemPlacement(placementAnimation),
        visible = !removing && !firstInit,
        enter = fadeIn(tween(visibilityDuration)) + slideInHorizontally(tween(visibilityDuration)) { (it * 1.25f).toInt() },
        exit = fadeOut(tween(visibilityDuration)) + slideOutHorizontally(tween(visibilityDuration)) { (it * 1.25f).toInt() }
    ) {
        Content(
            modifier = modifier,
            text = visuals.label,
            onDismiss = onDismissRequest,
            viewedTime = if (visuals.duration == PERMANENT) {
                null
            } else {
                durationAnimatable.value
            },
            totalTime = if (visuals.duration == PERMANENT) {
                null
            } else {
                visuals.duration.millis.toFloat()
            },
            style = visuals.style,
            leadingIcon = visuals.leadingIcon,
            onLeadingIconClick = visuals.leadingIconAction,
            trailingIcon = visuals.trailingIcon,
            onTrailingIconClick = visuals.trailingIconAction,
            textMaxWidth = visuals.textMaxWidth
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    text: String,
    onDismiss: () -> Unit,
    viewedTime: Float? = null,
    totalTime: Float? = null,
    style: JaArNotificationStyle = JaArNotificationStyle.TERTIARY,
    leadingIcon: JaArDynamicVector? = null,
    onLeadingIconClick: (() -> Unit)? = null,
    textMaxWidth: Dp = DEFAULT_MAX_TEXT_WIDTH,
    trailingIcon: JaArDynamicVector?,
    onTrailingIconClick: (() -> Unit)?,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(CORNER_SIZE))
            .background(style.container)
            .padding(CONTENT_PADDING),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LeadingIcon(
            modifier = Modifier.alpha(0.75f),
            leadingIcon = leadingIcon,
            onLeadingIconClick = onLeadingIconClick,
            tint = style.content,
            onDismiss = onDismiss,
        )
        Text(
            modifier = Modifier.widthIn(max = textMaxWidth),
            text = text,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = style.content

        )
        TrailingIcon(
            modifier = Modifier.alpha(0.75f),
            viewedTime = viewedTime,
            totalTime = totalTime,
            trailingIcon = trailingIcon,
            onTrailingIconClick = onTrailingIconClick,
            contentColor = style.content,
            onDismiss = onDismiss,
        )
    }
}

@Composable
private fun LeadingIcon(
    modifier: Modifier = Modifier,
    leadingIcon: JaArDynamicVector?,
    onLeadingIconClick: (() -> Unit)?,
    tint: Color,
    onDismiss: () -> Unit,
) {
    if (onLeadingIconClick != null && leadingIcon != null) { // clickable leadingIcon
        IconButton(
            modifier = modifier.size(ICON_BOX_SIZE),
            onClick = {
                onLeadingIconClick()
                onDismiss()
            }
        ) {
            Icon(
                modifier = Modifier.size(ICON_SIZE),
                imageVector = leadingIcon.vector,
                contentDescription = leadingIcon.contentDescription?.let { stringResource(id = it) },
                tint = tint

            )
        }
    } else if (leadingIcon != null) {
        Box(
            modifier = modifier.size(ICON_BOX_SIZE),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(ICON_SIZE),
                imageVector = leadingIcon.vector,
                contentDescription = leadingIcon.contentDescription?.let { stringResource(id = it) },
                tint = tint
            )
        }
    } else {
        Box(
            modifier = Modifier.size(ICON_BOX_SIZE / 2),
        )
    }
}

@Composable
private fun TrailingIcon(
    modifier: Modifier = Modifier,
    viewedTime: Float?,
    totalTime: Float?,
    contentColor: Color,
    trailingIcon: JaArDynamicVector?,
    onTrailingIconClick: (() -> Unit)?,
    onDismiss: () -> Unit,
) {
    IconButton(
        modifier = modifier.size(ICON_BOX_SIZE),
        onClick = {
            onTrailingIconClick?.let { it() }
            onDismiss()
        }
    ) {
        Icon(
            modifier = Modifier
                .size(ICON_SIZE)
                .drawBehind {
                    if (viewedTime != null && totalTime != null) {
                        drawArc(
                            color = contentColor,
                            startAngle = -90f,
                            sweepAngle = (viewedTime / totalTime * 360f),
                            useCenter = false,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                },
            imageVector = trailingIcon?.vector ?: Icons.Default.Close,
            contentDescription = stringResource(id = R.string.dismiss),
            tint = contentColor
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun JaArNotificationPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            JaArNotificationStyle.entries.forEach {
                Content(
                    text = it.name.lowercase(),
                    style = it,
                    onDismiss = { /*TODO*/ },
                    trailingIcon = null,
                    onTrailingIconClick = null,
                )
            }
        }
    }
}