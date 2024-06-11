package com.dev.bayan.ibrahim.feature.library.ui.screens.word

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavRoute.TopLevel.LIBRARY

internal fun NavGraphBuilder.words(
    screenSize: JaArScreenSize,
    onNavigateUp: () -> Unit,
    onEditWord: (Long?) -> Unit,
    onEditCategory: (Long?) -> Unit,
) {
    composable(
        route = LIBRARY.route
    ) {
        WordsRoute(
            screenSize = screenSize,
            onNavigateUp = onNavigateUp,
            onEditWord = onEditWord,
            onEditCategory = onEditCategory,
        )
    }
}