package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.category

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.common.model.INVALID_ID
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavRoute.LibraryEditorCategory

internal fun NavGraphBuilder.categoryEditor(
    screenSize: JaArScreenSize,
    onNavigateUp: () -> Unit,
) {
    composable(
        route = LibraryEditorCategory.route,
        arguments = listOf(
            navArgument(
                name = LibraryEditorCategory.Args.category_id.name,
                builder = {
                    this.type = NavType.LongType
                    defaultValue = INVALID_ID
                }
            )
        )
    ) {
        val id = it.arguments?.getLong(LibraryEditorCategory.Args.category_id.name)?.run {
            if (this == INVALID_ID) null else this
        }
        CategoryEditorRoute(
            screenSize = screenSize,
            wordId = id,
            onNavigateUp = onNavigateUp
        )
    }
}