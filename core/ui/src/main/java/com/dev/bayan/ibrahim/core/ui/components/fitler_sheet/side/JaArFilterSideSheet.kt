package com.dev.bayan.ibrahim.core.ui.components.fitler_sheet.side


import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString

@Composable
fun JaArFilterSideSheet(
    modifier: Modifier = Modifier,
    title: JaArDynamicString?,
    animationEasing: Easing = FastOutSlowInEasing,
    onDismissRequest: () -> Unit,
    primaryAction: @Composable() (RowScope.() -> Unit)?,
    secondaryAction: @Composable() (RowScope.() -> Unit)?,
    tertiaryAction: @Composable() (() -> Unit)?,
    content: @Composable () -> Unit,
) {
    var isClosing: Boolean by remember { mutableStateOf(false) }
    ModalSideSheet(
        modifier = modifier,
        visible = !isClosing,
        title = title,
        animationEasing = animationEasing,
        onDismissRequest = { isClosing = true },
        onEndCollapseAnimation = onDismissRequest,
        primaryAction = primaryAction,
        secondaryAction = secondaryAction,
        tertiaryAction = tertiaryAction,
        content = content,
    )
}