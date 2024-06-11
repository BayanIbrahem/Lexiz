package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.common.model.CategoryItem
import com.dev.bayan.ibrahim.core.common.model.TypeItem
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction
import com.dev.bayan.ibrahim.core.ui.components.action.JaArActionStyle
import com.dev.bayan.ibrahim.core.ui.components.action.checkable.JaArCheckable
import com.dev.bayan.ibrahim.core.ui.components.action.clicable.JaArClickable
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.input_field.JaArBasicInputField
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeHorizontalExpandEnterTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeHorizontalShrinkExitTransition
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.BuilderSetupSubscreenTemplate
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import com.dev.bayan.ibrahim.core.ui.R as UiRes

@Composable
internal fun NewFilterPage(
    modifier: Modifier = Modifier,

    screenSize: JaArScreenSize,
    // ui state
    uiState: NewFilterUiState,
    // ui event
    onEvent: (NewFilterEvent) -> Unit,
    // db flow
    allTypes: PersistentList<TypeItem>,
    allCategories: PersistentList<CategoryItem>,
    showCancelButton: Boolean,

    ) {
    val onTitleChange: (String) -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterEvent.OnNameChange(it)) } }
    }
    val onSaveAsCopyClick: (Boolean) -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterEvent.OnToggleSaveAsNewCopy(it)) } }
    }
    val onShowAll: (Boolean) -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterEvent.OnShowAll) } }
    }

    val onPrimaryAction: () -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterEvent.OnConfirm) } }
    }
    val onSecondaryAction: () -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterEvent.OnCancel) } }
    }
    val onTertiaryAction: () -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterEvent.OnApplyWithoutSave) } }
    }
    val filterLabels by remember(
        key1 = allTypes,
        key2 = allCategories,
        key3 = uiState.selectedFilter
    ) {
        derivedStateOf {
            when (uiState.selectedFilter) {
                is Filter.Category -> allCategories.map { it.id to it.name }
                is Filter.Type -> allTypes.map { it.id to "${it.languageCode}:${it.name}" }
                else -> persistentListOf()
            }.toPersistentList()
        }

    }
    LaunchedEffect(key1 = Unit) {
        onEvent(NewFilterEvent.InitScreen)
    }
    val name = uiState.selectedFilter?.name?.value
    LaunchedEffect(key1 = uiState.selectedFilter?.key) {
        uiState.selectedFilter?.name?.let {
            if (it.valueOrNull == null && name != null) {
                onEvent(NewFilterEvent.OnNameChange(name))
            }
        }
        onEvent(NewFilterEvent.InitScreen)
    }
    BackHandler(onBack = onSecondaryAction)
    BuilderSetupSubscreenTemplate(
        modifier = modifier,
        shortScreen = screenSize.isShort(),
        title = {
            uiState.selectedFilter?.let {
                JaArBasicInputField(
                    value = it.name.value,
                    onValueChange = onTitleChange,
                )
            } ?: Text(
                text = pluralStringResource(id = UiRes.plurals.filter, 10),
            )
        },
        description = R.string.quiz_newFilter_description,
        flags = {
            HorizontalDivider(
                modifier = Modifier
                    .width(150.dp)
                    .alpha(0.75f),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            AnimatedVisibility(
                visible = uiState.selectedFilter.run { this is Filter.Type || this is Filter.Category },
                enter = fadeHorizontalExpandEnterTransition(Alignment.Start),
                exit = fadeHorizontalShrinkExitTransition(Alignment.Start),
            ) {
                JaArCheckable(
                    checked = !uiState.showAll && uiState.enableHideExtraItemsFlag,
                    enabled = uiState.enableHideExtraItemsFlag,
                    label = JaArDynamicString.StrRes(
                        if (uiState.selectedFilter is Filter.Type) {
                            R.string.hide_extra_types
                        } else {
                            R.string.hide_extra_categories
                        }
                    ),
                    supportingText = JaArDynamicString.StrRes(
                        if (uiState.selectedFilter is Filter.Type) {
                            R.string.show_first_language_types_subtitle
                        } else {
                            R.string.show_first_language_categories_subtitle
                        }
                    ),
                    type = JaArAction.Checkable.CHECK_BOX,
                    onClick = onShowAll,
                )
            }
            JaArCheckable(
                checked = uiState.saveAsNewCopy ?: true,
                enabled = uiState.saveAsNewCopy != null,
                label = JaArDynamicString.StrRes(UiRes.string.save_as_copy),
                type = JaArAction.Checkable.CHECK_BOX,
                onClick = onSaveAsCopyClick,
            )
        },
        primaryAction = {
            JaArClickable(
                type = JaArAction.Clickable.TEXT_BUTTON,
                style = JaArActionStyle.PRIMARY_CONTAINER,
                label = JaArDynamicString.StrRes(UiRes.string.save_and_apply),
                enabled = uiState.selectedFilter?.validData ?: false,
                onClick = onPrimaryAction,
            )

        },
        secondaryAction = {
            if (showCancelButton) {
                JaArClickable(
                    type = JaArAction.Clickable.TEXT_BUTTON,
                    style = JaArActionStyle.PRIMARY_CONTAINER,
                    label = JaArDynamicString.StrRes(UiRes.string.cancel),
                    onClick = onSecondaryAction,
                )
            }
        },
        tertiaryAction = {
            JaArClickable(
                type = JaArAction.Clickable.TEXT_BUTTON,
                style = JaArActionStyle.PRIMARY_CONTAINER,
                label = JaArDynamicString.StrRes(UiRes.string.apply),
                enabled = uiState.selectedFilter?.validData ?: false,
                onClick = onTertiaryAction,
            )
        },
    ) {
        NewFilterPageContent(
            modifier = Modifier.weight(1f),
            screenSize = screenSize,
            filter = uiState.selectedFilter,
            showAll = uiState.showAll,
            allowedIds = when (uiState.selectedFilter) {
                is Filter.Category -> uiState.firstLanguageCategories
                is Filter.Type -> uiState.firstLanguageTypes
                else -> null
            },
            multiChoiceLabels = filterLabels,
            unAllowedFilters = uiState.unAllowedFilterKeys,
            onEvent = onEvent,
        )
    }
}
