package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter_group

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction
import com.dev.bayan.ibrahim.core.ui.components.action.JaArActionStyle
import com.dev.bayan.ibrahim.core.ui.components.action.checkable.JaArCheckable
import com.dev.bayan.ibrahim.core.ui.components.action.clicable.JaArClickable
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.input_field.JaArBasicInputField
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.BuilderSetupSubscreenTemplate
import com.dev.bayan.ibrahim.core.ui.R as UiRes


@Composable
internal fun NewFilterGroupPage(
    modifier: Modifier = Modifier,
    shortScreen: Boolean,

    uiState: FilterGroup,
    onEvent: (NewFilterGroupEvent) -> Unit,
    showAddTemplateFilterButton: Boolean,
    showAddNewFilterButton: Boolean,
) {
    val onTitleChange: (String) -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterGroupEvent.OnTitleChange(it)) } }
    }
    val onToggleSaveAsCopy: (Boolean) -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterGroupEvent.OnToggleSaveAsCopy(it)) } }
    }
    val onConfirm: () -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterGroupEvent.OnConfirm) } }
    }
    val onCancel: () -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterGroupEvent.OnCancel) } }
    }
    val onApply: () -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterGroupEvent.OnApplyWithoutSave) } }
    }
    val onAddNewFilter: () -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterGroupEvent.OnAddNewFilter) } }
    }
    val onAddTemplateFilter: () -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterGroupEvent.OnAddTemplateFilter) } }
    }
    val onEditFilter: (Int) -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterGroupEvent.OnEditFilter(it)) } }
    }
    val onRemoveFilter: (Int) -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterGroupEvent.OnRemoveFilter(it)) } }
    }
    LaunchedEffect(key1 = Unit) {
        onEvent(NewFilterGroupEvent.InitScreen)
    }
    val name = uiState.name.value
    LaunchedEffect(key1 = uiState.name) {
        if (uiState.name.valueOrNull == null) {
            onEvent(NewFilterGroupEvent.OnTitleChange(name))
        }
    }
    BackHandler(onBack = onCancel)
    BuilderSetupSubscreenTemplate(
        modifier = modifier,
        shortScreen = shortScreen,
        title = {
            JaArBasicInputField(
                value = uiState.name.value,
                onValueChange = onTitleChange,
                prefix = JaArDynamicString.StrRes(R.string.group_name)
            )
        },
        description = R.string.quiz_newGroup_description,
        flags = {
            JaArCheckable(
                checked = uiState.saveAsNewCopy,
                enabled = uiState.id != null,
                label = JaArDynamicString.StrRes(UiRes.string.save_as_copy),
                type = JaArAction.Checkable.CHECK_BOX,
                onClick = onToggleSaveAsCopy
            )
        },
        primaryAction = {
            JaArClickable(
                type = JaArAction.Clickable.TEXT_BUTTON,
                enabled = uiState.validData,
                style = JaArActionStyle.PRIMARY_CONTAINER,
                label = JaArDynamicString.StrRes(UiRes.string.save_and_apply),
                onClick = onConfirm
            )
        },
        secondaryAction = {
            JaArClickable(
                type = JaArAction.Clickable.TEXT_BUTTON,
                style = JaArActionStyle.PRIMARY_CONTAINER,
                label = JaArDynamicString.StrRes(UiRes.string.cancel),
                onClick = onCancel
            )
        },
        tertiaryAction = {
            JaArClickable(
                type = JaArAction.Clickable.TEXT_BUTTON,
                enabled = uiState.validData,
                style = JaArActionStyle.PRIMARY_CONTAINER,
                label = JaArDynamicString.StrRes(UiRes.string.apply),
                onClick = onApply
            )
        }
    ) {
        NewFilterGroupPageContent(
            modifier = Modifier.weight(1f),
            filterTypes = uiState.filters,
            onAddNewFilter = onAddNewFilter,
            onAddTemplateFilter = onAddTemplateFilter,
            onRemoveFilter = onRemoveFilter,
            onEditFilter = onEditFilter,
            showAddTemplateFilterButton = showAddTemplateFilterButton,
            showAddNewFilterButton = showAddNewFilterButton,
        )
    }
}

