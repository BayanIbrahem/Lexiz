package com.dev.bayan.ibrahim.feature.library.ui.screens.word

import com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.filter_sheet.WordsFilterSheetEvent
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.utils.WordsSelectionMenu


/**
 * actions for content screen in library feature
 */
internal data class WordsScreenActions(
    val onNavigateUp: () -> Unit,
    val onDropDownMenuItemClick: (item: WordsSelectionMenu) -> Unit,
    val selectionMenuOptionEnability: (WordsSelectionMenu) -> Boolean,
    val onClickCard: (key: Long) -> Unit,
    val onLongClickCard: (key: Long) -> Unit,
    val onDoubleClickCard: (key: Long) -> Unit,
    val onCancelSelectionMode: () -> Unit,
    val onAddNewWord: () -> Unit,
    val onAddNewCategory: () -> Unit,
    val onFilterSheetEvent: (event: WordsFilterSheetEvent) -> Unit,
)