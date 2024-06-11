package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainer
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.components.two_pane.FixedFirstPaneMinWidthHorizontalTwoPaneStrategy
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.core.filter_groups.BuilderQuizLazyFilterGroups
import com.google.accompanist.adaptive.TwoPane
import kotlinx.collections.immutable.PersistentList

@Composable
internal fun QuizSetupScreenContent(
    modifier: Modifier = Modifier,
    screenSize: JaArScreenSize,
    // ui state:
    uiState: QuizSetupUiState,

    // event:
    onEvent: (QuizSetupEvent) -> Unit,
    allLanguages: PersistentList<LanguageItem>,
) {
    if (screenSize.isCompat()) {
        QuizSetupMainInfoPane(
            modifier = modifier.fillMaxSize(),
            showTemplateGroupsList = true,
            uiState = uiState,
            onEvent = onEvent,
            allLanguages = allLanguages
        )
    } else {
        TwoPane(
            modifier = modifier.fillMaxSize(),
            first = {
                QuizSetupMainInfoPane(
                    modifier = modifier.fillMaxSize(),
                    showTemplateGroupsList = false,
                    uiState = uiState,
                    onEvent = onEvent,
                    allLanguages = allLanguages
                )
            },
            second = {
                val onEditGroup: (Int) -> Unit by remember {
                    derivedStateOf { { onEvent(QuizSetupEvent.OnEditGroup(it)) } }
                }
                val onRemoveGroup: (Int) -> Unit by remember {
                    derivedStateOf { { onEvent(QuizSetupEvent.OnRemoveGroup(it)) } }
                }
                val onAddNewGroup: () -> Unit by remember {
                    derivedStateOf { { onEvent(QuizSetupEvent.OnAddNewGroup) } }
                }
                val onAddTemplateGroup: () -> Unit by remember {
                    derivedStateOf { { onEvent(QuizSetupEvent.OnAddTemplateGroup) } }
                }
                JaArContainer(
                    modifier = modifier.fillMaxSize(),
                    type = JaArContainerType.PRIMARY,
                    gradient = true,
                ) {
                    BuilderQuizLazyFilterGroups(
                        modifier = Modifier.fillMaxSize(),
                        groups = uiState.groups,
                        onEditGroup = onEditGroup,
                        onRemoveGroup = onRemoveGroup,
                        onAddNewGroup = onAddNewGroup,
                        onAddTemplateGroup = onAddTemplateGroup,
                    )
                }
            },
            strategy = FixedFirstPaneMinWidthHorizontalTwoPaneStrategy(
                gapWidth = dimensionResource(id = com.dev.bayan.ibrahim.core.ui.R.dimen.main_container_content_spacedBy),
                firstPaneMinWidth = 300.dp,
                firstPaneWidthFraction = 0.2f
            ),
            displayFeatures = listOf(),
        )
    }
}