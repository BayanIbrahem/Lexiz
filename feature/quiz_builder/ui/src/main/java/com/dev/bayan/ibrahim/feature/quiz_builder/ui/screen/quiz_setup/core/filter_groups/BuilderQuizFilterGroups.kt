package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.core.filter_groups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction
import com.dev.bayan.ibrahim.core.ui.components.action.JaArActionStyle
import com.dev.bayan.ibrahim.core.ui.components.action.clicable.JaArClickable
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.template_group.TemplateFilterGroupItem
import kotlinx.collections.immutable.PersistentList
import com.dev.bayan.ibrahim.core.ui.R as UiRes

@Composable
internal fun BuilderQuizFilterGroups(
    modifier: Modifier = Modifier,
    groups: PersistentList<FilterGroup>,
    onEditGroup: (Int) -> Unit,
    onRemoveGroup: (Int) -> Unit,
    onAddNewGroup: () -> Unit,
    onAddTemplateGroup: () -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        BuilderQuizFilterTitle()
        groups.forEachIndexed { i, group ->
            TemplateFilterGroupItem(
                data = group,
                onClick = null,
                onEditGroup = { onEditGroup(i) },
                onRemove = { onRemoveGroup(i) },
                enabled = true
            )
        }
        BuilderQuizFilterGroupsNoGroups(noGroups = groups.isEmpty())
        BuilderQuizFilterGroupsActions(
            onAddNewGroup = onAddNewGroup,
            onAddTemplateGroup = onAddTemplateGroup,
        )
    }
}

@Composable
internal fun BuilderQuizLazyFilterGroups(
    modifier: Modifier = Modifier,
    groups: PersistentList<FilterGroup>,
    onEditGroup: (Int) -> Unit,
    onRemoveGroup: (Int) -> Unit,
    onAddNewGroup: () -> Unit,
    onAddTemplateGroup: () -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        item { BuilderQuizFilterTitle() }
        item { BuilderQuizFilterGroupsNoGroups(noGroups = groups.isEmpty()) }
        itemsIndexed(groups) { i, group ->
            TemplateFilterGroupItem(
                data = group,
                onClick = null,
                onEditGroup = { onEditGroup(i) },
                onRemove = { onRemoveGroup(i) },
                enabled = true
            )
        }
        item {
            BuilderQuizFilterGroupsActions(
                onAddNewGroup = onAddNewGroup,
                onAddTemplateGroup = onAddTemplateGroup,
            )
        }
    }
}

@Composable
private fun BuilderQuizFilterGroupsActions(
    modifier: Modifier = Modifier,
    onAddNewGroup: () -> Unit,
    onAddTemplateGroup: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JaArClickable(
            type = JaArAction.Clickable.TEXT_BUTTON,
            label = JaArDynamicString.StrRes(UiRes.string.add_new),
            style = JaArActionStyle.PRIMARY_CONTAINER,
            onClick = onAddNewGroup,
        )
        JaArClickable(
            type = JaArAction.Clickable.TEXT_BUTTON,
            label = JaArDynamicString.StrRes(UiRes.string.add_template),
            style = JaArActionStyle.PRIMARY_CONTAINER,
            onClick = onAddTemplateGroup,
        )
    }
}

@Composable
private fun BuilderQuizFilterGroupsNoGroups(
    modifier: Modifier = Modifier,
    noGroups: Boolean,
) {
    if (noGroups) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = pluralStringResource(id = UiRes.plurals.group, 0),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BuilderQuizFilterTitle(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = pluralStringResource(id = UiRes.plurals.group, 10),
        style = MaterialTheme.typography.titleMedium
    )
}
