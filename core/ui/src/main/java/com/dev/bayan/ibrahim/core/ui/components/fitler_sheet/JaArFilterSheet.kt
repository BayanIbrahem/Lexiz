package com.dev.bayan.ibrahim.core.ui.components.fitler_sheet


import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.fitler_sheet.bottom.JaArFilterBottomSheet
import com.dev.bayan.ibrahim.core.ui.components.fitler_sheet.side.JaArFilterSideSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JaArFilterSheet(
    modifier: Modifier = Modifier,
    state: SheetState,
    isBottomSheet: Boolean,
    bottomSheetContentHeight: Dp = 400.dp,
    title: JaArDynamicString?,
    animationEasing: Easing = FastOutSlowInEasing,
    onDismissRequest: () -> Unit,
    primaryAction: (@Composable RowScope.() -> Unit)?,
    secondaryAction: (@Composable RowScope.() -> Unit)?,
    tertiaryAction: (@Composable () -> Unit)?,
    content: @Composable () -> Unit,
) {
    val isVisible by remember(state) {
        derivedStateOf { state.isVisible }
    }
    if (isVisible) {
        if (isBottomSheet) {
            JaArFilterBottomSheet(
                modifier = modifier,
                title = title,
                state = state,
                contentHeight = bottomSheetContentHeight,
                onDismissRequest = onDismissRequest,
                content = content,
                primaryAction = primaryAction,
                secondaryAction = secondaryAction,
                tertiaryAction = tertiaryAction
            )
        } else {
            JaArFilterSideSheet(
                modifier = modifier,
                title = title,
                animationEasing = animationEasing,
                onDismissRequest = onDismissRequest,
                primaryAction = primaryAction,
                secondaryAction = secondaryAction,
                tertiaryAction = tertiaryAction,
                content = content,
            )
        }
    }
}

