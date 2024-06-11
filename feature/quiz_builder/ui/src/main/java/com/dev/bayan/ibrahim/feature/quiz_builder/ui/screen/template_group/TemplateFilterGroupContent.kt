package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.template_group


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet

@Composable
internal fun ColumnScope.TemplateFilterGroupContent(
    modifier: Modifier = Modifier,
    groups: PersistentList<FilterGroup>,
    onClickGroup: (Int) -> Unit,
    onEditGroup: (Int) -> Unit,
    onRemoveGroup: (Int) -> Unit,
    quizSelectedIds: PersistentSet<Long>,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        modifier = modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        itemsIndexed(groups) { i, group ->
            TemplateFilterGroupItem(
                data = group,
                enabled = group.id !in quizSelectedIds,
                onClick = { onClickGroup(i) },
                onEditGroup = { onEditGroup(i) },
                onRemove = { onRemoveGroup(i) },
            )
        }
    }
}
