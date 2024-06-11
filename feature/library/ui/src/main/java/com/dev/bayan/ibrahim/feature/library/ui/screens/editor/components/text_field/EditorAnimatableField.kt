package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeHorizontalExpandEnterTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeHorizontalShrinkExitTransition

@Composable
internal fun EditorAnimatableField(
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    visible: Boolean,
    isCompat: Boolean,
    uiState: EditorFieldUiState,
    uiActions: EditorFieldUiAction,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeHorizontalExpandEnterTransition(Alignment.Start),
        exit = fadeHorizontalShrinkExitTransition(Alignment.Start)
    ) {
        EditorField(
            modifier = Modifier.fillMaxWidth(),
            data = uiState,
            hasSupportingText = isCompat,
            actions = uiActions,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
    }
}