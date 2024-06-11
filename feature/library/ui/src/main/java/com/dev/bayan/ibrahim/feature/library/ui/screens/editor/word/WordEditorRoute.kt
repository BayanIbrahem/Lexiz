package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.word

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.feature.library.ui.theme.JaArLibraryTheme

@Composable
internal fun WordEditorRoute(
    modifier: Modifier = Modifier,
    wordViewModel: WordEditorViewModel = hiltViewModel(),
    screenSize: JaArScreenSize,
    wordId: Long? = null,
    onNavigateUp: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        wordViewModel.initWord(wordId)
    }
//   JaArLibraryTheme {
    WordEditorScreen(
        modifier = modifier,
        uiState = wordViewModel.getUiState(),
        uiActions = wordViewModel.getUiActions(onNavigateUp),
        screenSize = screenSize,
    )
//   }
}