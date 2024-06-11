package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.template_group

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.divider.DividerWithSubtitle
import com.dev.bayan.ibrahim.core.ui.components.dynamic_text.JaArDynamicText
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.filter_item.FilterValueGridItem
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterAppearanceStatus
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.color
import kotlinx.collections.immutable.PersistentList

@Composable
internal fun TemplateFilterGroupItem(
    modifier: Modifier = Modifier,
    data: FilterGroup,
    enabled: Boolean,
    onClick: (() -> Unit)?,
    onEditGroup: () -> Unit,
    onRemove: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable(
                enabled = onClick != null && enabled,
            ) {
                if (onClick != null && enabled) {
                    onClick()
                }
            },
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BuilderFilterGroupTemplateDataTitle(
            title = data.name.value,
            onEditGroup = onEditGroup,
            onRemove = onRemove,
        )
        GroupFilters(filters = data.filters)
        DividerWithSubtitle(
            subtitle = stringResource(id = data.saveStatus.resName),
            subtitleAlignment = Alignment.TopEnd,
            textColor = data.saveStatus.color
        )
    }
}

@Composable
private fun ColumnScope.BuilderFilterGroupTemplateDataTitle(
    modifier: Modifier = Modifier,
    title: String,
    onEditGroup: () -> Unit,
    onRemove: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        JaArDynamicText(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
        )
        IconButton(onClick = onEditGroup) {
            Icon(Icons.Filled.Edit, null)
        }
        IconButton(onClick = onRemove) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun ColumnScope.GroupFilters(
    modifier: Modifier = Modifier,
    filters: PersistentList<Filter>,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.quizBuilder_filterItem_instanceSpacedBy))
    ) {
        items(filters) {
            FilterValueGridItem(
                value = it,
                appearanceStatus = FilterAppearanceStatus.NORMAL,
                onClick = null,
            )
        }
    }
}
