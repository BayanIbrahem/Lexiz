package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.core.top_app_bar.QuizTopAppBar
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.top_app_bar.QuizTopAppBarDropDownMenu
import kotlinx.collections.immutable.PersistentList

@Composable
internal fun QuizSetupScreen(
    modifier: Modifier = Modifier,
    screenSize: JaArScreenSize,
    // ui state:
    uiState: QuizSetupUiState,
    // event:
    onEvent: (QuizSetupEvent) -> Unit,
    // db flow
    allLanguages: PersistentList<LanguageItem>,
) {
    val menuEvent: (QuizTopAppBarDropDownMenu) -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnClickTopAppBarMenuItem(it)) } }
    }
    val navigateUp: () -> Unit by remember {
        derivedStateOf { { onEvent(QuizSetupEvent.OnNavigateUp) } }
    }
    LaunchedEffect(key1 = Unit) {
        onEvent(QuizSetupEvent.InitScreen)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = com.dev.bayan.ibrahim.core.ui.R.dimen.main_container_content_spacedBy))
    ) {
        QuizTopAppBar(
            isTemplateQuiz = uiState.id != null,
            savingState = uiState.savingState,
            consumeTemplate = uiState.consumeTemplate,
            onClickMenuItem = menuEvent,
            onNavigateUp = navigateUp,
        )
        QuizSetupScreenContent(
            modifier = modifier.weight(1f),
            screenSize = screenSize,
            uiState = uiState,
            onEvent = onEvent,
            allLanguages = allLanguages,
        )
    }
}