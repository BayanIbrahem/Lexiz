package com.dev.bayan.ibrahim.feature.quiz_builder.ui.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.common.model.CategoryItem
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.common.model.TypeItem
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.graph.QuizRoutes.QuizSetup
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.graph.QuizRoutes.NewGroup
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.graph.QuizRoutes.TemplateGroups
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.NewFilterGroupScreen
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter.NewFilterEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter.NewFilterUiState
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter_group.NewFilterGroupEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.template_filter.TemplateFilterEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.template_filter.TemplateFiltersUiState
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen.QuizSetupEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen.QuizSetupScreen
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen.QuizSetupUiState
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.template_group.TemplateFilterGroupEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.template_group.TemplateFilterGroupScreen
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.template_group.TemplateFilterGroupUiState
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet

@Composable
internal fun QuizNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    screenSize: JaArScreenSize,
    // quiz setup
    quizSetupUiState: QuizSetupUiState,
    onQuizSetupEvent: (QuizSetupEvent) -> Unit,
    // template group
    templateFilterGroupUiState: TemplateFilterGroupUiState,
    onTemplateFilterGroupEvent: (TemplateFilterGroupEvent) -> Unit,
    quizSelectedIds: PersistentSet<Long>,
    // new group
    newFilterGroupUiState: FilterGroup,
    templateFilterUiState: TemplateFiltersUiState,
    newFilterUiState: NewFilterUiState,
    onNewFilterGroupEvent: (NewFilterGroupEvent) -> Unit,
    onTemplateFilterEvent: (TemplateFilterEvent) -> Unit,
    onNewFilterEvent: (NewFilterEvent) -> Unit,
    // db flow
    allLanguages: PersistentList<LanguageItem>,
    allTypes: PersistentList<TypeItem>,
    allCategories: PersistentList<CategoryItem>,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = QuizSetup.route
    ) {
//      composable(
//         route = TemplateQuizes.route
//      ) {
//
//      }
        composable(
            route = QuizSetup.route
        ) {
            QuizSetupScreen(
                screenSize = screenSize,
                uiState = quizSetupUiState,
                onEvent = onQuizSetupEvent,
                allLanguages = allLanguages,
            )
        }
        composable(
            route = TemplateGroups.route
        ) {
            TemplateFilterGroupScreen(
                shortScreen = screenSize.isShort(),
                uiState = templateFilterGroupUiState,
                onEvent = onTemplateFilterGroupEvent,
                quizSelectedIds = quizSelectedIds,
            )
        }
        composable(
            route = NewGroup.route
        ) {
            NewFilterGroupScreen(
                screenSize = screenSize,
                group = newFilterGroupUiState,
                templateFiltersUiState = templateFilterUiState,
                newFilterUiState = newFilterUiState,
                onGroupEvent = onNewFilterGroupEvent,
                onTemplateFilterEvent = onTemplateFilterEvent,
                onNewFilterEvent = onNewFilterEvent,
                allTypes = allTypes,
                allCategories = allCategories,
            )
        }
    }
}