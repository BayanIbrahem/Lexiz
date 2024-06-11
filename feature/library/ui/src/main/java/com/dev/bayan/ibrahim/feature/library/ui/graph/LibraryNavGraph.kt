package com.dev.bayan.ibrahim.feature.library.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavRoute
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavRoute.TopLevel.LIBRARY
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.category.categoryEditor
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.word.wordEditor
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.words

fun NavGraphBuilder.library(
    screenSize: JaArScreenSize,
    onNavigateUp: () -> Unit,
    onEditWord: (Long?) -> Unit,
    onEditCategory: (Long?) -> Unit,
) {
    words(
        screenSize = screenSize,
        onNavigateUp = onNavigateUp,
        onEditWord = onEditWord,
        onEditCategory = onEditCategory
    )
    wordEditor(
        screenSize = screenSize,
        onNavigateUp = onNavigateUp
    )
    categoryEditor(
        screenSize = screenSize,
        onNavigateUp = onNavigateUp
    )
}