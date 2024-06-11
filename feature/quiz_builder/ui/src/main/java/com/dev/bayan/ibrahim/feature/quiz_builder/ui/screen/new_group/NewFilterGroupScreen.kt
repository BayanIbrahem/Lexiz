package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.AnimatedPane
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.PaneAdaptedValue
import androidx.compose.material3.adaptive.PaneScaffoldDirective
import androidx.compose.material3.adaptive.SupportingPaneScaffold
import androidx.compose.material3.adaptive.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.ThreePaneScaffoldAdaptStrategies
import androidx.compose.material3.adaptive.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.common.model.CategoryItem
import com.dev.bayan.ibrahim.core.common.model.TypeItem
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter.NewFilterEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter.NewFilterPage
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter.NewFilterUiState
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter_group.NewFilterGroupEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter_group.NewFilterGroupPage
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.template_filter.TemplateFilterEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.template_filter.TemplateFiltersPage
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.template_filter.TemplateFiltersUiState
import kotlinx.collections.immutable.PersistentList

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun NewFilterGroupScreen(
    modifier: Modifier = Modifier,
    screenSize: JaArScreenSize,
    // ui state:
    group: FilterGroup,
    templateFiltersUiState: TemplateFiltersUiState,
    newFilterUiState: NewFilterUiState,
    // event:
    onGroupEvent: (NewFilterGroupEvent) -> Unit,
    onTemplateFilterEvent: (TemplateFilterEvent) -> Unit,
    onNewFilterEvent: (NewFilterEvent) -> Unit,
    // db flow
    allTypes: PersistentList<TypeItem>,
    allCategories: PersistentList<CategoryItem>,
) {
    val navigator = rememberSupportingPaneScaffoldNavigator(
        scaffoldDirective = PaneScaffoldDirective(
            contentPadding = PaddingValues(0.dp),
            maxHorizontalPartitions = if (screenSize.isCompat()) 1 else if (screenSize.isMedium()) 2 else 3,
            horizontalPartitionSpacerSize = 8.dp,
            maxVerticalPartitions = if (screenSize.isShort()) 1 else 2,
            verticalPartitionSpacerSize = 8.dp,
            excludedBounds = emptyList()
        ),
    )
    val destinationsHistory by remember { derivedStateOf { mutableListOf<ThreePaneScaffoldRole>() } }
    val showCancelButton by remember(navigator.scaffoldState.scaffoldValue.primary) {
        derivedStateOf {
            navigator.scaffoldState.scaffoldValue.primary == PaneAdaptedValue.Hidden
        }
    }
    val showAddTemplateFilterButton by remember(navigator.scaffoldState.scaffoldValue.secondary) {
        derivedStateOf {
            navigator.scaffoldState.scaffoldValue.secondary == PaneAdaptedValue.Hidden
        }
    }
    val showAddNewFilterButton by remember(navigator.scaffoldState.scaffoldValue.tertiary) {
        derivedStateOf {
            navigator.scaffoldState.scaffoldValue.tertiary == PaneAdaptedValue.Hidden
        }
    }
    val groupEvent: (NewFilterGroupEvent) -> Unit by remember {
        derivedStateOf {
            {
                onGroupEvent(it)
                when (it) {
                    NewFilterGroupEvent.OnAddNewFilter -> {
                        navigator.singleNavigateTo(
                            pane = SupportingPaneScaffoldRole.Extra,
                            history = destinationsHistory
                        )
                    }

                    NewFilterGroupEvent.OnAddTemplateFilter -> {
                        navigator.singleNavigateTo(
                            pane = SupportingPaneScaffoldRole.Supporting,
                            history = destinationsHistory
                        )
                    }

                    is NewFilterGroupEvent.OnEditFilter -> {
                        navigator.singleNavigateTo(
                            pane = SupportingPaneScaffoldRole.Extra,
                            history = destinationsHistory
                        )
                    }

                    else -> { /*already handled*/
                    }
                }
            }
        }
    }
    val templateFilterEvent: (TemplateFilterEvent) -> Unit by remember {
        derivedStateOf {
            {
                onTemplateFilterEvent(it)
                when (it) {
                    TemplateFilterEvent.OnCancel, TemplateFilterEvent.OnConfirm -> {
                        navigator.singleNavigateUp(
                            pane = SupportingPaneScaffoldRole.Supporting,
                            history = destinationsHistory
                        )
                    }

                    is TemplateFilterEvent.OnClickFilter -> {
                        navigator.singleNavigateTo(
                            pane = SupportingPaneScaffoldRole.Supporting,
                            history = destinationsHistory
                        )
                    }

                    else -> {

                    }
                }
            }
        }
    }
    val newFilterEvent: (NewFilterEvent) -> Unit by remember {
        derivedStateOf {
            {
                onNewFilterEvent(it)
                when (it) {
                    NewFilterEvent.OnCancel,
                    NewFilterEvent.OnConfirm,
                    NewFilterEvent.OnApplyWithoutSave,
                    -> {
                        navigator.singleNavigateUp(
                            SupportingPaneScaffoldRole.Extra,
                            history = destinationsHistory
                        )
                    }

                    is NewFilterEvent.OnSelectType -> {
                        navigator.singleNavigateTo(
                            pane = SupportingPaneScaffoldRole.Extra,
                            history = destinationsHistory
                        )
                    }

                    else -> {}
                }
            }
        }
    }
    SupportingPaneScaffold(
        modifier = modifier.fillMaxSize(),
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        scaffoldState = navigator.scaffoldState,
        mainPane = {
            AnimatedPane(modifier = Modifier.fillMaxSize()) {
                NewFilterGroupPage(
                    modifier = Modifier.fillMaxSize(),
                    shortScreen = screenSize.isShort(),
                    uiState = group,
                    onEvent = groupEvent,
                    showAddTemplateFilterButton = showAddTemplateFilterButton,
                    showAddNewFilterButton = showAddNewFilterButton,
                )
            }
        },
        supportingPane = {
            AnimatedPane(modifier = Modifier.fillMaxSize()) {
                TemplateFiltersPage(
                    modifier = Modifier.fillMaxSize(),
                    shortScreen = screenSize.isShort(),
                    uiState = templateFiltersUiState,
                    onEvent = templateFilterEvent,
                    showCancelButton = showCancelButton,
                )
            }
        },
        extraPane = {
            AnimatedPane(modifier = Modifier.fillMaxSize()) {
                NewFilterPage(
                    modifier = Modifier.fillMaxSize(),
                    screenSize = screenSize,
                    uiState = newFilterUiState,
                    onEvent = newFilterEvent,
                    allTypes = allTypes,
                    allCategories = allCategories,
                    showCancelButton = showCancelButton,
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun ThreePaneScaffoldNavigator.singleNavigateTo(
    pane: ThreePaneScaffoldRole,
    history: MutableList<ThreePaneScaffoldRole>,
) {
    if (history.lastOrNull() != pane) {
        navigateTo(pane)
        history.add(pane)
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun ThreePaneScaffoldNavigator.singleNavigateUp(
    pane: ThreePaneScaffoldRole,
    history: MutableList<ThreePaneScaffoldRole>,
) {
    while (history.lastOrNull() == pane) {
        navigateBack(false)
        history.removeLastOrNull()
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun ThreePaneScaffoldNavigator.singleNavigateUpTop(
    history: MutableList<ThreePaneScaffoldRole>,
) {
    history.removeLastOrNull()?.let {
        while (history.lastOrNull() == it) {
            navigateBack(false)
            history.removeLastOrNull()
        }
    }
}
