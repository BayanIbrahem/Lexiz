package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.title

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeVerticalExpandEnterTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeVerticalShrinkExitTransition

@Composable
internal fun EditorTitle(
    modifier: Modifier = Modifier,
    title: JaArDynamicString,
    isShort: Boolean
) {
    AnimatedVisibility(
        visible = !isShort,
        enter = fadeVerticalExpandEnterTransition(Alignment.Top),
        exit = fadeVerticalShrinkExitTransition(Alignment.Top),
    ) {// title container, visible if not short
        Text(
            modifier = modifier,
            text = title.value,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

