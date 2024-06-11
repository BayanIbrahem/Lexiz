package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter_group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.filter_item.FilterValueListItem
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterType
import kotlinx.collections.immutable.PersistentList
import com.dev.bayan.ibrahim.core.ui.R as UiRes


@Composable
internal fun ColumnScope.NewFilterGroupPageContent(
    modifier: Modifier = Modifier,

    filterTypes: PersistentList<FilterType>,

    showAddTemplateFilterButton: Boolean,
    showAddNewFilterButton: Boolean,

    onAddNewFilter: () -> Unit,
    onAddTemplateFilter: () -> Unit,
    onRemoveFilter: (Int) -> Unit,
    onEditFilter: (Int) -> Unit,
) {
    Column(
        modifier = modifier.weight(1f),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = pluralStringResource(id = UiRes.plurals.filter, count = 10),
                style = MaterialTheme.typography.titleSmall,
            )
            if (showAddNewFilterButton) {
                IconButton(onClick = onAddNewFilter) {
                    Icon(Icons.Filled.Add, null)
                }
            }
            if (showAddTemplateFilterButton) {
                IconButton(onClick = onAddTemplateFilter) {
                    Icon(Icons.Filled.PostAdd, null)
                }
            }
        }
        LazyColumn(
            modifier = Modifier.heightIn(dimensionResource(id = R.dimen.quizBuilder_filterItem_listHeight))
        ) {
            itemsIndexed(filterTypes) { i, it ->
                FilterValueListItem(
                    value = it,
                    onEdit = { onEditFilter(i) },
                    onRemove = { onRemoveFilter(i) },
                )
            }
        }
    }
}