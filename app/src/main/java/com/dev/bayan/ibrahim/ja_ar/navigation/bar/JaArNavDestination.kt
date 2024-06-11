package com.dev.bayan.ibrahim.ja_ar.navigation.bar


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.navigation.NavRouteType
import com.dev.bayan.ibrahim.core.ui.utils.animation.animNav

@Composable
fun JaArNavDestination(
    modifier: Modifier = Modifier,
    destination: NavRouteType.TopLevel,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val alpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0.75f,
        animationSpec = tween(animNav),
        label = "",
    )
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                if (!selected) {
                    onClick()
                }
            }
            .graphicsLayer { this.alpha = alpha },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        DestinationIcon(
            data = destination,
            selected = selected,
        )
        Text(
            text = destination.routeName.value,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) {
                MaterialTheme.colorScheme.onSecondaryContainer
            } else {
                MaterialTheme.colorScheme.onPrimaryContainer
            }
        )
    }
}

@Composable
private fun DestinationIcon(
    data: NavRouteType.TopLevel,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    val containerColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            Color.Transparent
        },
        animationSpec = tween(animNav),
        label = ""
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.onSecondaryContainer
        } else {
            MaterialTheme.colorScheme.onPrimaryContainer
        },
        animationSpec = tween(animNav),
        label = ""
    )
    Box(
        modifier = modifier
            .height(32.dp)
            .width(64.dp)
            .drawBehind {
                drawRoundRect(
                    color = containerColor,
                    cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx()),
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = (if (selected) data.selectedIcon else data.unselectedIcon).vector,
            contentDescription = data.routeName.value,
            tint = contentColor,
        )
    }
}
