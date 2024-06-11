package com.dev.bayan.ibrahim.core.ui.components.labled_icon

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.utils.animation.animLibSteps

@Composable
fun JaArLabeledIcon(
    modifier: Modifier = Modifier,
    data: LabeledIconData,
    active: Boolean = true,
) {
    val alpha by animateFloatAsState(
        targetValue = if (active) 1f else 0.38f,
        animationSpec = tween(animLibSteps),
    )
    Row(
        modifier = modifier
            .graphicsLayer { this.alpha = alpha },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            imageVector = data.icon.vector,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = data.label.value,
            modifier = Modifier
        )
    }
}
