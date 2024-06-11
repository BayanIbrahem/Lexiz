package com.dev.bayan.ibrahim.core.ui.components.container


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.utils.animation.animContainerSelection

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JaArContainer(
    modifier: Modifier = Modifier,
    type: JaArContainerType,
    enabled: Boolean = true,
    gradient: Boolean = true,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    fab: @Composable() (BoxScope.() -> Unit)? = null,
    floatingNotifications: @Composable() (BoxScope.() -> Unit)? = null,
    content: @Composable() (BoxScope.() -> Unit),
) {
    val containerColor: Color = type.containerColor
    val radius = dimensionResource(id = R.dimen.floating_primary_dialog_corner_size)
    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.38f,
        animationSpec = tween(animContainerSelection),
        label = ""
    )
    val clickModifier by remember(onClick, onLongClick, onDoubleClick) {
        derivedStateOf {
            onClick?.let {
                Modifier
                    .combinedClickable(
                        onClick = onClick,
                        onLongClick = onLongClick,
                        onDoubleClick = onDoubleClick
                    )
            } ?: Modifier
        }
    }
    Box(
        modifier = modifier
            .graphicsLayer { this.alpha = alpha }
            .clip(RoundedCornerShape(radius))
            .drawBehind {
                drawRoundRect(
                    color = containerColor,
                    cornerRadius = CornerRadius(radius.toPx())
                )
            }
            .then(clickModifier),
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            content()
        }
        floatingNotifications?.let {
            Box(modifier = Modifier.align(Alignment.TopEnd)) { it() }
        }
        fab?.let { it() }
    }
}

