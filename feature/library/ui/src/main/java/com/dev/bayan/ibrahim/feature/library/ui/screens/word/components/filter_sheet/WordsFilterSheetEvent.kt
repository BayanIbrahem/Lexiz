package com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.filter_sheet

import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupSortBy

internal sealed interface WordsFilterSheetEvent {
    data object OnApply : WordsFilterSheetEvent
    data object OnCancel : WordsFilterSheetEvent
    data object OnReset : WordsFilterSheetEvent
    data class OnGroupBy(val groupBy: WordsGroupBy) : WordsFilterSheetEvent
    data class OnSortBy(val sortBy: WordsGroupSortBy) : WordsFilterSheetEvent
    data class OnReverseSort(val wasReversed: Boolean) : WordsFilterSheetEvent
    data class OnSelectLanguage(val code: String, val wasSelected: Boolean) :
        WordsFilterSheetEvent

    data class OnSelectType(val id: Long, val wasSelected: Boolean) :
        WordsFilterSheetEvent

    data class OnSelectCategory(val id: Long, val wasSelected: Boolean) :
        WordsFilterSheetEvent


    fun onApplyEvent(state: WordsFilterSheetState): WordsFilterSheetState {
        return when (this) {
            OnApply -> state
            OnCancel -> state
            OnReset -> state
            is OnGroupBy -> state.copy(groupBy = groupBy)
            is OnSortBy -> state.copy(sortBy = sortBy)
            is OnReverseSort -> state.copy(reverseSort = !wasReversed)
            is OnSelectLanguage -> state.copy(
                unselectedLanguages = state.unselectedLanguages.run {
                    if (wasSelected) add(code) else remove(code)
                }
            )

            is OnSelectType -> state.copy(
                unselectedTypes = state.unselectedTypes.run {
                    if (wasSelected) add(id) else remove(id)
                }
            )

            is OnSelectCategory -> state.copy(
                unselectedCategories = state.unselectedCategories.run {
                    if (wasSelected) add(id) else remove(id)
                }
            )
        }
    }
}