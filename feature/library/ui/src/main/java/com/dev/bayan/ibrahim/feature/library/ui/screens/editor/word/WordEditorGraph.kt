package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.word

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.common.model.INVALID_ID
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavRoute.LibraryEditorWord

internal fun NavGraphBuilder.wordEditor(
    screenSize: JaArScreenSize,
    onNavigateUp: () -> Unit,
) {
    composable(
        route = LibraryEditorWord.route,
        arguments = listOf(
            navArgument(
                name = LibraryEditorWord.Args.word_id.name,
                builder = {
                    this.type = NavType.LongType
                    defaultValue = INVALID_ID
                }
            )
        )
    ) {
        val id = it.arguments?.getLong(LibraryEditorWord.Args.word_id.name)?.run {
            if (this == INVALID_ID) null else this
        }
        WordEditorRoute(
            screenSize = screenSize,
            wordId = id,
            onNavigateUp = onNavigateUp
        )
    }
}