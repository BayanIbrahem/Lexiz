package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.feature.library.ui.theme.JaArLibraryTheme

@Composable
internal fun CategoryEditorRoute(
    modifier: Modifier = Modifier,
    wordViewModel: CategoryEditorViewModel = hiltViewModel(),
    screenSize: JaArScreenSize,
    wordId: Long? = null,
    onNavigateUp: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        wordViewModel.initCategory(wordId)
    }
//   JaArLibraryTheme {
    CategoryEditorScreen(
        modifier = modifier,
        uiState = wordViewModel.getUiState(),
        uiActions = wordViewModel.getUiActions(onNavigateUp),
        screenSize = screenSize,
    )
//   }
}