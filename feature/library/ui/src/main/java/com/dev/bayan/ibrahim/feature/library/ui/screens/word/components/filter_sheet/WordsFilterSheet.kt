package com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.filter_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupSortBy
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction
import com.dev.bayan.ibrahim.core.ui.components.action.checkable.JaArCheckable
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectable
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectableItem
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.drop_down.JaArTextDropDownMenu
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.selecatable_group.JaArSelectableGroup
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.components.dynamic.asDynamicString
import com.dev.bayan.ibrahim.core.ui.components.fitler_sheet.JaArFilterSheet
import com.dev.bayan.ibrahim.core.ui.res.actionOnModelResource
import com.dev.bayan.ibrahim.core.ui.res.thereNoModel
import com.dev.bayan.ibrahim.feature.library.domain.model.words.WordBaseDataSet
import com.dev.bayan.ibrahim.feature.ui.R
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import com.dev.bayan.ibrahim.core.ui.R as UiRes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WordsFilterSheet(
    modifier: Modifier = Modifier,
    isBottomSheet: Boolean,
    sheetState: SheetState,
    baseDataSet: WordBaseDataSet,
    dataState: WordsFilterSheetState,
    onEvent: (WordsFilterSheetEvent) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val onHide: () -> Unit by remember {
        mutableStateOf({ scope.launch { sheetState.hide() } })
    }
    val onApplyWithDismiss by remember {
        mutableStateOf({ onEvent(WordsFilterSheetEvent.OnApply); onHide() })
    }
    val onCancelWithDismiss by remember {
        mutableStateOf({ onEvent(WordsFilterSheetEvent.OnCancel); onHide() })
    }
    val onReset by remember {
        mutableStateOf({ onEvent(WordsFilterSheetEvent.OnReset) })
    }
    val onSelectGroupByItem: (JaArSelectable, Boolean) -> Unit by remember {
        mutableStateOf({ i, _ -> onEvent(WordsFilterSheetEvent.OnGroupBy(WordsGroupBy.entries[i.id.toInt()])) })
    }
    val onSelectSortByItem: (JaArSelectable, Boolean) -> Unit by remember {
        mutableStateOf({ i, _ -> onEvent(WordsFilterSheetEvent.OnSortBy(WordsGroupSortBy.entries[i.id.toInt()])) })
    }
    val onReverseSort: (Boolean) -> Unit by remember {
        mutableStateOf({ onEvent(WordsFilterSheetEvent.OnReverseSort(it)) })
    }
    JaArFilterSheet(
        modifier = modifier,
        isBottomSheet = isBottomSheet,
        state = sheetState,
        title = JaArDynamicString.StrRes(R.string.words_filter),
        bottomSheetContentHeight = 400.dp,
        onDismissRequest = { scope.launch { sheetState.hide() } },
        primaryAction = {
            Button(onClick = onApplyWithDismiss) {
                Text(text = stringResource(id = UiRes.string.apply))
            }
        },
        secondaryAction = {
            OutlinedButton(onClick = onCancelWithDismiss) {
                Text(text = stringResource(id = UiRes.string.cancel))
            }
        },
        tertiaryAction = {
            TextButton(onClick = onReset) {
                Text(text = stringResource(id = UiRes.string.reset))
            }
        },
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // group by
            JaArTextDropDownMenu(
                fillMaxWidth = true,
                title = JaArDynamicString.StrRes(UiRes.string.group_by),
                selected = dataState.groupBy.asSelectable(),
                options = WordsGroupBy.selectableEntries,
                defaultLabel = WordsGroupBy.selectableEntries.first().label,
                onSelectItem = onSelectGroupByItem,
                leadingIcon = JaArDynamicVector.Vector(Icons.Filled.Layers)
            )
            // sort by
            Column {
                JaArTextDropDownMenu(
                    fillMaxWidth = true,
                    title = JaArDynamicString.StrRes(UiRes.string.sort_by),
                    selected = dataState.sortBy.asSelectable(),
                    options = WordsGroupSortBy.selectableEntries,
                    defaultLabel = WordsGroupSortBy.selectableEntries.first().label,
                    onSelectItem = onSelectSortByItem,
                    leadingIcon = JaArDynamicVector.Vector(Icons.AutoMirrored.Filled.Sort)
                )
                JaArCheckable(
                    checked = dataState.reverseSort,
                    label = JaArDynamicString.StrRes(UiRes.string.reverse),
                    type = JaArAction.Checkable.CHECK_BOX,
                    onClick = onReverseSort,
                )
            }
            // languages:
            val languagesOptions by remember(dataState.unselectedLanguages, baseDataSet) {
                derivedStateOf {
                    baseDataSet.allLanguages.map {
                        JaArSelectableItem(it.languageCode.asDynamicString()) to (it.languageCode !in dataState.unselectedLanguages)
                    }.toPersistentList()
                }
            }
            JaArSelectableGroup(
                options = languagesOptions,
                emptyListText = JaArDynamicString.Builder {
                    thereNoModel(item = UiRes.plurals.language, there = false)
                },
                label = JaArDynamicString.Builder {
                    actionOnModelResource(
                        action = UiRes.string.select,
                        model = UiRes.plurals.language,
                        count = 10,
                    )
                },
                title = JaArDynamicString.PluralRes(UiRes.plurals.language, 10),
                leadingIcon = JaArDynamicVector.Res(UiRes.drawable.ic_language),
                itemType = JaArAction.Checkable.CHECK_BOX,
                onSelectItem = { index, wasSelected: Boolean ->
                    baseDataSet.allLanguages.getOrNull(index)?.let {
                        onEvent(
                            WordsFilterSheetEvent.OnSelectLanguage(
                                it.languageCode,
                                wasSelected,
                            )
                        )
                    }
                }
            )
            // type:
            val typesOptions by remember(dataState.unselectedTypes, baseDataSet.allTypes) {
                derivedStateOf {
                    baseDataSet.allTypes.map {
                        JaArSelectableItem(
                            JaArDynamicString.Str(
                                str = "${it.languageCode}:${it.name}",
                                key = it.id
                            )
                        ) to (it.id !in dataState.unselectedTypes)
                    }.toPersistentList()
                }
            }
            JaArSelectableGroup(
                options = typesOptions,
                emptyListText = JaArDynamicString.Builder {
                    thereNoModel(item = UiRes.plurals.type, there = false)
                },
                label = JaArDynamicString.Builder {
                    actionOnModelResource(
                        action = UiRes.string.select,
                        model = UiRes.plurals.type,
                        count = 10,
                    )
                },
                title = JaArDynamicString.PluralRes(UiRes.plurals.type, 10),
                leadingIcon = JaArDynamicVector.Res(UiRes.drawable.ic_type),
                itemType = JaArAction.Checkable.CHECK_BOX,
                onSelectItem = { index, wasSelected: Boolean ->
                    baseDataSet.allTypes.getOrNull(index)?.let {
                        onEvent(
                            WordsFilterSheetEvent.OnSelectType(
                                it.id,
                                wasSelected,
                            )
                        )
                    }
                }
            )
            // categories:
            val categoriesOptions by remember(
                dataState.unselectedCategories,
                baseDataSet.allCategories
            ) {
                derivedStateOf {
                    baseDataSet.allCategories.map {
                        JaArSelectableItem(
                            JaArDynamicString.Str(str = it.name, key = it.id)
                        ) to (it.id !in dataState.unselectedCategories)
                    }.toPersistentList()
                }
            }
            JaArSelectableGroup(
                options = categoriesOptions,
                emptyListText = JaArDynamicString.Builder {
                    thereNoModel(item = UiRes.plurals.category, there = false)
                },
                label = JaArDynamicString.Builder {
                    actionOnModelResource(
                        action = UiRes.string.select,
                        model = UiRes.plurals.category,
                        count = 10,
                    )
                },
                title = JaArDynamicString.PluralRes(UiRes.plurals.category, 10),
                leadingIcon = JaArDynamicVector.Res(UiRes.drawable.ic_category),
                itemType = JaArAction.Checkable.CHECK_BOX,
                onSelectItem = { index, wasSelected: Boolean ->
                    baseDataSet.allCategories.getOrNull(index)?.let {
                        onEvent(
                            WordsFilterSheetEvent.OnSelectCategory(
                                it.id,
                                wasSelected,
                            )
                        )
                    }
                }
            )
        }
    }
}