package com.dev.bayan.ibrahim.feature.library.ui.screens.word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dev.bayan.ibrahim.feature.library.domain.model.words.Word
import com.dev.bayan.ibrahim.feature.library.domain.model.words.WordBaseDataSet
import com.dev.bayan.ibrahim.feature.library.domain.use_case.words.LibraryWordsBaseDataSetCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.words.LibraryWordsDeleteCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.words.LibraryWordsLoadCase
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.filter_sheet.WordsFilterSheetEvent
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.utils.WordsSelectionMenu
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.filter_sheet.WordsFilterSheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentHashSetOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class WordsScreenViewModel @Inject constructor(
    private val loadWordUseCase: LibraryWordsLoadCase,
    dataSetUseCase: LibraryWordsBaseDataSetCase,
    private val deleteWordUseCase: LibraryWordsDeleteCase,
) : ViewModel() {

    private val _wordsFlow: MutableStateFlow<PagingData<Word>> =
        MutableStateFlow(PagingData.empty())
    val wordsFlow = _wordsFlow.asStateFlow()

    val wordBaseDataSet = dataSetUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WordBaseDataSet()
    )

    private fun loadWords(filterArgs: WordsFilterSheetState) {
        viewModelScope.launch {
            loadWordUseCase(
                unselectedLanguages = filterArgs.unselectedLanguages,
                unselectedTypes = filterArgs.unselectedTypes,
                unselectedCategories = filterArgs.unselectedCategories,
                groupBy = filterArgs.groupBy,
                sortBy = filterArgs.sortBy,
                reverseSort = filterArgs.reverseSort
            ).distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    var prevGroupName: String? = null
                    _wordsFlow.value = it.map {
                        it.copy(
                            groupName = if (prevGroupName == it.groupName) {
                                null
                            } else {
                                prevGroupName = it.groupName
                                prevGroupName
                            }
                        )
                    }
                }
        }
    }

    fun initWords() {
        loadWords(WordsFilterSheetState())
    }

    private var appliedFilters = WordsFilterSheetState()

    private val _userInputState = MutableStateFlow(WordsScreenUserInputState())
    val userInputState = _userInputState.asStateFlow()

    private val _filterSheetState = MutableStateFlow(WordsFilterSheetState())
    val filterSheetState = _filterSheetState.asStateFlow()


    fun onClickCard(id: Long) {
        viewModelScope.launch {
            if (_userInputState.value.isSelectionMode) {
                _userInputState.update {
                    val selectedCards = it.selectedCardsIds.run {
                        if (contains(id)) remove(id) else add(id)
                    }
                    it.copy(
                        selectedCardsIds = selectedCards,
                    )
                }
            } else {
                _userInputState.update {
                    it.copy(focusedCard = id)
                }
            }
        }
    }

    fun onLongClickCard(id: Long) {
        viewModelScope.launch {
            if (_userInputState.value.isSelectionMode) {
                // long click on selection mode acts like single click on non selection
                _userInputState.update {
                    it.copy(focusedCard = id)
                }
            } else {
                _userInputState.update {
                    val selectedCardsIds = persistentHashSetOf(id)
                    it.copy(
                        isSelectionMode = true,
                        selectedCardsIds = selectedCardsIds,
                    )
                }
            }
        }
    }

    fun onDoubleClickCard(key: Long) {
        viewModelScope.launch {
            if (_userInputState.value.isSelectionMode) {
                // double click on selection mode acts as single click
                onClickCard(key)
            } else {
                // TODO, normal mode card double click
            }
        }
    }


    // selection:
    fun onCancelSelectionMode() {
        viewModelScope.launch {
            _userInputState.update {
                it.copy(
                    isSelectionMode = false,
                    selectedCardsIds = persistentHashSetOf(),
                )
            }
        }
    }


    fun onDropDownMenuItemSelect(item: WordsSelectionMenu, onEditWord: (Long?) -> Unit) {
        if (userInputState.value.isSelectionMode) { // selection mode
            when (item) {
                WordsSelectionMenu.DELETE -> {
                    onDropDownMenuItemDeleteSelect()
                }

                WordsSelectionMenu.DESELECT -> onDropDownMenuItemDeselectAll()
                WordsSelectionMenu.UPDATE -> {
                    userInputState.value.selectedCardsIds.run {
                        if (count() == 1) {
                            onEditWord(first())
                        }
                    }
                }
            }
            if (item.cancelSelectionMode) {
                onCancelSelectionMode()
            }
        }
    }

    private fun onDropDownMenuItemDeleteSelect() {
        viewModelScope.launch {
            val selectedCards = _userInputState.value.selectedCardsIds
            if (deleteWordUseCase(selectedCards) > 0) {
                loadWords(appliedFilters)
            }
        }
    }

    private fun onDropDownMenuItemDeselectAll() {
        viewModelScope.launch {
            _userInputState.update {
                it.copy(
                    selectedCardsIds = persistentHashSetOf(),
                )
            }
        }
    }

    fun selectionMenuOptionEnability(item: WordsSelectionMenu): Boolean {
        userInputState.value.apply {
            return this.isSelectionMode && when (item) {
                WordsSelectionMenu.DELETE -> this.selectedCardsIds.isNotEmpty()
                WordsSelectionMenu.UPDATE -> this.selectedCardsIds.count() == 1
                WordsSelectionMenu.DESELECT -> this.selectedCardsIds.isNotEmpty()
            }
        }
    }

    fun onFilterSheetEvent(event: WordsFilterSheetEvent) {
        _filterSheetState.update { event.onApplyEvent(it) }
        when (event) {
            WordsFilterSheetEvent.OnApply -> onApplyFilterSheet()
            WordsFilterSheetEvent.OnCancel -> onCancelFilterSheet()
            WordsFilterSheetEvent.OnReset -> onResetFilterSheet()
            else -> {
                /* already handled */
            }
        }
    }

    private fun onApplyFilterSheet() {
        // TODO save from state
        appliedFilters = _filterSheetState.value
        loadWords(appliedFilters)
    }

    private fun onCancelFilterSheet() {
        _filterSheetState.update {
            appliedFilters
        }
    }

    private fun onResetFilterSheet() {
        appliedFilters = WordsFilterSheetState()
        onCancelFilterSheet()
    }
}