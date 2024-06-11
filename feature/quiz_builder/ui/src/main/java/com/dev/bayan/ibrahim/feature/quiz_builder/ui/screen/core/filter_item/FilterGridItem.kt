package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.filter_item

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainer
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.utils.animation.animBuilderFilterItem
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterAppearanceStatus

@Composable
internal fun FilterGridItem(
    modifier: Modifier = Modifier,
    appearanceStatus: FilterAppearanceStatus,
    width: Dp,
    @DrawableRes iconRes: Int,
    name: String,
    label: String?,
    onClick: (() -> Unit)?,
) {
    val enableAlpha by animateFloatAsState(
        targetValue = when (appearanceStatus) {
            FilterAppearanceStatus.UNAVAILABLE -> 0.38f
            else -> 1f
        },
        animationSpec = tween(animBuilderFilterItem), label = "",
    )
    val selectionAlpha by animateFloatAsState(
        targetValue = when (appearanceStatus) {
            FilterAppearanceStatus.SELECTED -> 1f
            else -> 0f
        },
        animationSpec = tween(animBuilderFilterItem), label = "",
    )
    val iconSelectionColor by animateColorAsState(
        targetValue = when (appearanceStatus) {
            FilterAppearanceStatus.SELECTED -> MaterialTheme.colorScheme.onSecondary
            else -> MaterialTheme.colorScheme.onSecondaryContainer
        },
        animationSpec = tween(animBuilderFilterItem), label = "",
    )
    val nameSelectionColor by animateColorAsState(
        targetValue = when (appearanceStatus) {
            FilterAppearanceStatus.SELECTED -> MaterialTheme.colorScheme.secondary
            else -> MaterialTheme.colorScheme.onSecondaryContainer
        },
        animationSpec = tween(animBuilderFilterItem), label = "",
    )
    JaArContainer(
        modifier = modifier
            .width(width),
        type = JaArContainerType.SECONDARY,
        gradient = true,
        onClick = if (appearanceStatus != FilterAppearanceStatus.UNAVAILABLE && onClick != null) onClick else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer { this.alpha = enableAlpha },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // todo set size to resources and check for best value of the size
            val secondary = MaterialTheme.colorScheme.secondary
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .drawBehind {
                        drawCircle(color = secondary, alpha = selectionAlpha)
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = iconSelectionColor,
                )
            }
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = nameSelectionColor,
                textAlign = TextAlign.Center,
            )
            if (label != null) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
