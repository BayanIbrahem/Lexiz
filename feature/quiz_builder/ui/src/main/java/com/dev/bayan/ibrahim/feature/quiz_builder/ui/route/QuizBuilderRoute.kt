package com.dev.bayan.ibrahim.feature.quiz_builder.ui.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.view_model.QuizViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavActions
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.graph.QuizNavGraph
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.graph.QuizRoutes
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter.NewFilterEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter_group.NewFilterGroupEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.template_filter.TemplateFilterEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen.QuizSetupEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.template_group.TemplateFilterGroupEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.theme.JaArQuizTheme


@Composable
internal fun QuizBuilderRoute(
    modifier: Modifier = Modifier,
    screenSize: JaArScreenSize,
    quizViewModel: QuizViewModel = hiltViewModel(),
    onFeatureNavigateUp: () -> Unit,
) {
    val navController = rememberNavController()
    val navActions by remember { derivedStateOf { JaArNavActions(navController) } }
    // ui state
    val newFiltersUiState by quizViewModel.newFiltersUiState.collectAsStateWithLifecycle()
    val templateFiltersUiState by quizViewModel.templateFiltersUiState.collectAsStateWithLifecycle()
    val newGroupUiState by quizViewModel.newGroupUiState.collectAsStateWithLifecycle()
    val templateGroupUiState by quizViewModel.templateGroupUiState.collectAsStateWithLifecycle()
    val quizSetupUiState by quizViewModel.quizSetupUiState.collectAsStateWithLifecycle()
    val quizSelectedIds by quizViewModel.quizSetupSelectedGroupsIds.collectAsStateWithLifecycle()

    // ui event
    val onQuizSetupEvent: (QuizSetupEvent) -> Unit by remember {
        derivedStateOf {
            {
                quizViewModel.onQuizSetupEvent(
                    event = it,
                    navigateToTemplateGroup = { navActions.jaArNavigateTo(QuizRoutes.TemplateGroups.getDestination()) },
                    navigateToNewGroup = { navActions.jaArNavigateTo(QuizRoutes.NewGroup.getDestination()) },
                    navigateUp = navActions::navigateUp,
                )
            }
        }
    }
    val onTemplateGroupEvent: (TemplateFilterGroupEvent) -> Unit by remember {
        derivedStateOf {
            {
                quizViewModel.onTemplateGroupEvent(
                    event = it,
                    navigateToNewGroup = { navActions.jaArNavigateTo(QuizRoutes.NewGroup.getDestination()) },
                    navigateUp = navActions::navigateUp,
                )
            }
        }
    }
    val onNewGroupEvent: (NewFilterGroupEvent) -> Unit by remember {
        derivedStateOf {
            {
                quizViewModel.onNewGroupEvent(
                    event = it,
                    navigateUp = navActions::navigateUp,
                )
            }
        }
    }
    val onTemplateFilterEvent: (TemplateFilterEvent) -> Unit by remember {
        derivedStateOf {
            {
                quizViewModel.onTemplateFilterEvent(
                    event = it,
                )
            }
        }
    }
    val onNewFilterEvent: (NewFilterEvent) -> Unit by remember {
        derivedStateOf {
            {
                quizViewModel.onNewFilterEvent(
                    event = it,
                )
            }
        }
    }
    // db flow
    val allLanguages by quizViewModel.allLanguages.collectAsStateWithLifecycle()
    val allTypes by quizViewModel.allTypes.collectAsStateWithLifecycle()
    val allCategories by quizViewModel.allCategories.collectAsStateWithLifecycle()

//   JaArQuizTheme {

    QuizNavGraph(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        screenSize = screenSize,

        quizSetupUiState = quizSetupUiState,
        onQuizSetupEvent = onQuizSetupEvent,

        templateFilterGroupUiState = templateGroupUiState,
        onTemplateFilterGroupEvent = onTemplateGroupEvent,
        quizSelectedIds = quizSelectedIds,

        newFilterGroupUiState = newGroupUiState,
        templateFilterUiState = templateFiltersUiState,
        newFilterUiState = newFiltersUiState,
        onNewFilterGroupEvent = onNewGroupEvent,
        onTemplateFilterEvent = onTemplateFilterEvent,
        onNewFilterEvent = onNewFilterEvent,

        allLanguages = allLanguages,
        allTypes = allTypes,
        allCategories = allCategories,
    )
//   }
}