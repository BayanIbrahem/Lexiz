package com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.filter_sheet

import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupSortBy
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf

internal data class WordsFilterSheetState(
    val groupBy: WordsGroupBy = WordsGroupBy.NONE,
    val sortBy: WordsGroupSortBy = WordsGroupSortBy.WORD,
    val reverseSort: Boolean = false,
    val unselectedLanguages: PersistentSet<String> = persistentSetOf(),
    val unselectedTypes: PersistentSet<Long> = persistentSetOf(),
    val unselectedCategories: PersistentSet<Long> = persistentSetOf(),
)