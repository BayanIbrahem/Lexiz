package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction
import com.dev.bayan.ibrahim.core.ui.components.action.checkable.JaArCheckable
import com.dev.bayan.ibrahim.core.ui.components.dynamic.asDynamicString
import com.dev.bayan.ibrahim.core.ui.components.slider.JaArValueRangeSlider
import com.dev.bayan.ibrahim.core.ui.components.slider.JaArValueSlider
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterType
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.FilterTypesGrid
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.filter.FilterValueEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.range.asFloatRange
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.range.asIntRange
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import kotlin.math.roundToInt

@Composable
internal fun NewFilterPageContent(
    modifier: Modifier = Modifier,
    screenSize: JaArScreenSize,

    filter: Filter?,
    showAll: Boolean,
    allowedIds: PersistentSet<Long>?,
    multiChoiceLabels: PersistentList<Pair<Long, String>>,
    unAllowedFilters: PersistentSet<Int>,
    onEvent: (NewFilterEvent) -> Unit,
) {
    val onSelectType: (Int) -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterEvent.OnSelectType(it)) } }
    }
    val onValueChange: (FilterValueEvent) -> Unit by remember {
        derivedStateOf { { onEvent(NewFilterEvent.OnFilterValueChange(it)) } }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        FilterTypesGrid(
            selectedFilter = filter,
            unAllowedFilters = unAllowedFilters,
            onSelectType = onSelectType,
            screenSize = screenSize,
        )
        filter?.let {
            FilterValueInput(
                filterType = it,
                onValueChange = onValueChange,
                allowedIds = allowedIds,
                multiChoiceLabels = multiChoiceLabels,
                showAll = showAll,
            )
        }
    }
}

@Composable
private fun ColumnScope.FilterValueInput(
    modifier: Modifier = Modifier,
    filterType: FilterType,
    allowedIds: PersistentSet<Long>?,
    multiChoiceLabels: PersistentList<Pair<Long, String>>,
    showAll: Boolean,
    onValueChange: (FilterValueEvent) -> Unit,
) {
    val onSliderValueChange: (Int) -> Unit by remember {
        derivedStateOf { { onValueChange(FilterValueEvent.OnValueChange(it)) } }
    }
    val onRangeSliderValueChange: (IntRange) -> Unit by remember {
        derivedStateOf { { onValueChange(FilterValueEvent.OnRangeChange(it)) } }
    }
    val onListSelectValueChange: (Long) -> Unit by remember {
        derivedStateOf { { onValueChange(FilterValueEvent.OnSelectItem(it)) } }
    }
    when (filterType) {
        is FilterType.Number -> {
            FilterValueSliderInput(
                modifier = modifier,
                value = filterType.value,
                valueRange = filterType.validRange,
                addInfinityAsLastValue = false,
                onValueChange = onSliderValueChange
            )
        }

        is FilterType.Percent -> {
            FilterValueSliderInput(
                modifier = modifier,
                value = filterType.value,
                valueRange = filterType.validRange,
                addInfinityAsLastValue = false,
                onValueChange = onSliderValueChange,
            )
        }

        is FilterType.Range -> {
            FilterValueSliderRangeInput(
                modifier = modifier,
                value = filterType.value,
                valueRange = filterType.validRange,
                addInfinityAsLastValue = filterType.infinityEnd,
                onValueChange = onRangeSliderValueChange,
            )
        }

        is FilterType.MultiChoice -> {
            FilterValueSelectFromListInput(
                modifier = modifier.weight(1f),
                itemsLabels = multiChoiceLabels,
                singleChoice = false,
                selectedItems = filterType.selectedValues,
                validIds = allowedIds,
                showAll = showAll,
                onClickItem = onListSelectValueChange,
            )
        }

        is FilterType.SingleChoice -> {
            FilterValueSelectFromListInput(
                modifier = modifier.weight(1f),
                itemsLabels = multiChoiceLabels,
                singleChoice = true,
                selectedItems = filterType.selectedValue?.let { persistentSetOf(it) }
                    ?: persistentSetOf(),
                validIds = allowedIds,
                showAll = showAll,
                onClickItem = onListSelectValueChange,
            )
        }
    }
}

@Composable
private fun FilterValueSliderInput(
    modifier: Modifier = Modifier,
    value: Int,
    addInfinityAsLastValue: Boolean,
    valueRange: IntRange,
    onValueChange: (Int) -> Unit,
) {
    val onFloatValueChange: (Float) -> Unit by remember {
        derivedStateOf { { onValueChange(it.roundToInt()) } }
    }
    JaArValueSlider(
        modifier = modifier,
        sliderValue = value.toFloat(),
        steps = valueRange.run { (last - first).inc() },
        inputValueRange = valueRange.asFloatRange(),
        addInfinityAsLastValue = addInfinityAsLastValue,
        actAsIntValue = true,
        onValueChange = onFloatValueChange
    )
}

@Composable
private fun FilterValueSliderRangeInput(
    modifier: Modifier = Modifier,
    value: IntRange,
    addInfinityAsLastValue: Boolean,
    valueRange: IntRange,
    onValueChange: (IntRange) -> Unit,
) {
    val onFloatValueChange: (ClosedFloatingPointRange<Float>) -> Unit by remember {
        derivedStateOf { { onValueChange(it.asIntRange()) } }
    }
    JaArValueRangeSlider(
        modifier = modifier,
        sliderValue = value.asFloatRange(),
        steps = valueRange.run { (last - first).inc() },
        inputValueRange = valueRange.asFloatRange(),
        addInfinityAsLastValue = addInfinityAsLastValue,
        actAsIntValue = true,
        onValueChange = onFloatValueChange
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilterValueSelectFromListInput(
    modifier: Modifier = Modifier,
    itemsLabels: PersistentList<Pair<Long, String>>,
    singleChoice: Boolean,
    showAll: Boolean,
    selectedItems: PersistentSet<Long>,
    onClickItem: (Long) -> Unit,
    validIds: PersistentSet<Long>?,
) {
    FlowRow(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.quizBuilder_filterItem_selectionOptionsSpacedBy)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.quizBuilder_filterItem_selectionOptionsSpacedBy)),
    ) {
        itemsLabels.forEach { (id, label) ->
            if (showAll || validIds?.run { id in this } != false) {
                JaArCheckable(
                    label = label.asDynamicString(),
                    checked = id in selectedItems,
                    enabled = true,
                    onClick = { onClickItem(id) },
                    type = if (singleChoice) JaArAction.Checkable.RADIO_BUTTON else JaArAction.Checkable.CHECK_BOX
                )
            }
        }
    }
}
