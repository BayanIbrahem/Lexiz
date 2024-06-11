package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.template_filter


import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction
import com.dev.bayan.ibrahim.core.ui.components.action.JaArActionStyle
import com.dev.bayan.ibrahim.core.ui.components.action.clicable.JaArClickable
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.BuilderSetupSubscreenTemplate
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.FilterValuesGrid
import com.dev.bayan.ibrahim.core.ui.R as UiRes

@Composable
internal fun TemplateFiltersPage(
    modifier: Modifier = Modifier,
    shortScreen: Boolean,
    uiState: TemplateFiltersUiState,
    onEvent: (TemplateFilterEvent) -> Unit,
    showCancelButton: Boolean,
) {
    val onConfirm by remember {
        derivedStateOf { { onEvent(TemplateFilterEvent.OnConfirm) } }
    }
    val onCancel by remember {
        derivedStateOf { { onEvent(TemplateFilterEvent.OnCancel) } }
    }
    val onRemove by remember {
        derivedStateOf { { onEvent(TemplateFilterEvent.OnRemove) } }
    }
    val onClickFilter: (Long) -> Unit by remember {
        derivedStateOf { { onEvent(TemplateFilterEvent.OnClickFilter(it)) } }
    }
    BackHandler(onBack = onCancel)
    BuilderSetupSubscreenTemplate(
        modifier = modifier,
        shortScreen = shortScreen,
        title = {
            Text(text = stringResource(R.string.template_filters))
        },
        description = R.string.quiz_templateFilter_description,
        flags = {},
        primaryAction = {
            JaArClickable(
                type = JaArAction.Clickable.TEXT_BUTTON,
                style = JaArActionStyle.PRIMARY_CONTAINER,
                label = JaArDynamicString.StrRes(UiRes.string.select),
                enabled = uiState.validData,
                onClick = onConfirm
            )
        },
        secondaryAction = {
            if (showCancelButton) {
                JaArClickable(
                    type = JaArAction.Clickable.TEXT_BUTTON,
                    style = JaArActionStyle.PRIMARY_CONTAINER,
                    label = JaArDynamicString.StrRes(UiRes.string.cancel),
                    onClick = onCancel
                )
            }
        },
        tertiaryAction = {
            JaArClickable(
                type = JaArAction.Clickable.TEXT_BUTTON,
                style = JaArActionStyle.PRIMARY_CONTAINER,
                label = JaArDynamicString.StrRes(UiRes.string.remove),
                enabled = uiState.validData,
                onClick = onRemove
            )
        },
    ) {
        FilterValuesGrid(
            modifier = Modifier.weight(1f),
            filters = uiState.filters,
            selectedId = uiState.selectedFilterId,
            unAllowedFilters = uiState.unAllowedFilterKeys,
            onClickFilter = onClickFilter,
        )
    }
}
