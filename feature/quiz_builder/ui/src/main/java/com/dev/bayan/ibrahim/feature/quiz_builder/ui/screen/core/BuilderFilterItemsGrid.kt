package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.common.null_coerce.coercedInOrNull
import com.dev.bayan.ibrahim.core.ui.components.divider.DividerWithSubtitle
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.filter_item.FilterTypeGridItem
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.filter_item.FilterValueGridItem
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterAppearanceStatus
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet

@Composable
internal fun FilterTypesGrid(
    modifier: Modifier = Modifier,
    screenSize: JaArScreenSize,
    selectedFilter: Filter?,
    unAllowedFilters: PersistentSet<Int>,
    onSelectType: (Int) -> Unit,
) {
    FiltersHorizontalGrid(
        modifier = modifier.height(if (screenSize.isExpandedHeight()) 180.dp else 84.dp), // (84 + 12) + 84
        rows = GridCells.Fixed(if (screenSize.isExpandedHeight()) 2 else 1),
        spacedBy = dimensionResource(id = R.dimen.quizBuilder_filterItem_templateSpacedBy),
        hint = selectedFilter?.resHint?.run { stringResource(id = this) },
        items = {
            items(
                items = Filter.getDefaultFilters(),
                key = { it.key },
            ) { filter ->
                FilterTypeGridItem(
                    type = filter,
                    appearanceStatus = if (selectedFilter?.key == filter.key) {
                        FilterAppearanceStatus.SELECTED
                    } else if (filter.key in unAllowedFilters) {
                        FilterAppearanceStatus.UNAVAILABLE
                    } else {
                        FilterAppearanceStatus.NORMAL
                    },
                    onClick = { onSelectType(filter.key) },
                )
            }
        }
    )
}

@Composable
internal fun FilterValuesGrid(
    modifier: Modifier = Modifier,
    filters: PersistentList<Filter>,
    selectedId: Long?,
    unAllowedFilters: PersistentSet<Int>,
    onClickFilter: (Long) -> Unit,
) {
    val resHint by remember(selectedId) {
        derivedStateOf { filters.firstOrNull { it.id == selectedId }?.resHint }
    }
    FiltersVerticalGrid(
        modifier = modifier,
        minSize = dimensionResource(id = R.dimen.quizBuilder_filterItem_instanceWidth),
        spacedBy = dimensionResource(id = R.dimen.quizBuilder_filterItem_instanceSpacedBy),
        hint = resHint?.let { stringResource(id = it) },
    ) {
        items(
            items = filters,
            key = { it.id!! }
        ) { filter ->
            FilterValueGridItem(
                value = filter,
                appearanceStatus = if (selectedId == filter.id) {
                    FilterAppearanceStatus.SELECTED
                } else if (filter.key in unAllowedFilters) {
                    FilterAppearanceStatus.UNAVAILABLE
                } else {
                    FilterAppearanceStatus.NORMAL
                },
                onClick = { onClickFilter(filter.id!!) },
            )
        }
    }
}

@Composable
internal fun FiltersVerticalGrid(
    modifier: Modifier = Modifier,
    minSize: Dp,
    spacedBy: Dp,
    hint: String?,
    items: LazyGridScope.() -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Adaptive(minSize = minSize),
            verticalArrangement = Arrangement.spacedBy(spacedBy),
            horizontalArrangement = Arrangement.spacedBy(spacedBy, Alignment.CenterHorizontally),
            content = items,
        )
        hint?.let {
            DividerWithSubtitle(
                modifier = Modifier.alpha(0.75f),
                subtitle = it,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@Composable
internal fun FiltersHorizontalGrid(
    modifier: Modifier = Modifier,
    rows: GridCells = GridCells.Fixed(2),
    spacedBy: Dp,
    hint: String?,
    items: LazyGridScope.() -> Unit,
) {
    LazyHorizontalGrid(
        modifier = modifier,
        rows = rows,
        verticalArrangement = Arrangement.spacedBy(spacedBy, Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(spacedBy, Alignment.Start),
        content = items,
    )
    hint?.let {
        DividerWithSubtitle(
            modifier = Modifier.alpha(0.75f),
            subtitle = it,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}
