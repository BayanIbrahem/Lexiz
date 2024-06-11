package com.dev.bayan.ibrahim.core.ui.components.topAppBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeHorizontalExpandEnterTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeHorizontalShrinkExitTransition

@Composable
fun JaArTopAppBar(
    modifier: Modifier = Modifier,
    title: JaArDynamicString,
    subtitle: JaArDynamicString?,
    isSelectionMode: Boolean,
    normalActions: (@Composable RowScope.() -> Unit)?,
    selectionActions: (@Composable RowScope.() -> Unit)?,
    onNavigateUp: (() -> Unit)?,
) {
    Row(
        modifier = modifier.height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // navigation button
        onNavigateUp?.let {
            IconButton(it) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }
        }
        // title - subtitle
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = title.value, maxLines = 1)
                subtitle?.let {
                    Text(
                        modifier = Modifier.alpha(0.5f),
                        text = it.value,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = isSelectionMode,
            enter = fadeHorizontalExpandEnterTransition(Alignment.End),
            exit = fadeHorizontalShrinkExitTransition(Alignment.Start),
        ) {
            selectionActions?.let {
                Row { it() }
            }
        }
        AnimatedVisibility(
            visible = !isSelectionMode,
            enter = fadeHorizontalExpandEnterTransition(Alignment.End),
            exit = fadeHorizontalShrinkExitTransition(Alignment.Start),
        ) {
            normalActions?.let {
                Row { it() }
            }
        }
    }
}