package com.dev.bayan.ibrahim.feature.quiz_builder.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavRoute
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.route.QuizBuilderRoute

fun NavGraphBuilder.quiz(
    screenSize: JaArScreenSize,
    onNavigateUp: () -> Unit
) {
    composable(
        route = JaArNavRoute.TopLevel.QUIZ.route,
    ) {
        QuizBuilderRoute(
            screenSize = screenSize,
            onFeatureNavigateUp = onNavigateUp,
        )
    }
}
