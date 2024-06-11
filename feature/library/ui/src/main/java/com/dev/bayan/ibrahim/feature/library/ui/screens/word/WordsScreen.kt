package com.dev.bayan.ibrahim.feature.library.ui.screens.word

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainer
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.components.fab.JaArFab
import com.dev.bayan.ibrahim.core.ui.res.thereNoModel
import com.dev.bayan.ibrahim.feature.library.domain.model.words.Word
import com.dev.bayan.ibrahim.feature.library.domain.model.words.WordBaseDataSet
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.WordsTopAppBar
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.card.WordCard
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.filter_sheet.WordsFilterSheetState
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.utils.WordsContainerFabOptions
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.utils.WordsContainerFabOptions.CATEGORY
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.utils.WordsContainerFabOptions.WORD
import com.dev.bayan.ibrahim.core.ui.R as CoreUi

@Composable
internal fun WordsScreen(
    modifier: Modifier = Modifier,
    actions: WordsScreenActions,
    userInputState: WordsScreenUserInputState,
    baseDataSet: WordBaseDataSet,
    filterSheetState: WordsFilterSheetState,
    paginatedWords: LazyPagingItems<Word>,
    screenSize: JaArScreenSize,
) {
    val spacedBy =
        dimensionResource(id = CoreUi.dimen.main_screen_content_spacedBy)
    val arrangement by remember { mutableStateOf(Arrangement.spacedBy(spacedBy)) }
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = arrangement,
    ) {
        WordsTopAppBar(
            modifier = modifier,
            isBottomSheet = screenSize.isCompat(),
            userInputData = userInputState,
            baseDataSet = baseDataSet,
            filterSheetState = filterSheetState,
            onClickDropDownMenuItem = actions.onDropDownMenuItemClick,
            onCancelSelectionMode = actions.onCancelSelectionMode,
            onFilterSheetEvent = actions.onFilterSheetEvent,
            selectionMenuOptionEnability = actions.selectionMenuOptionEnability
        )
        WordsCardsGrid(
            actions = actions,
            userInputState = userInputState,
            paginatedWords = paginatedWords,
            modifier = Modifier.weight(1f),

            )
    }
}

@Composable
private fun WordsCardsGrid(
    actions: WordsScreenActions,
    userInputState: WordsScreenUserInputState,
    paginatedWords: LazyPagingItems<Word>,
    modifier: Modifier = Modifier,
) {
    JaArContainer(
        modifier = modifier.fillMaxWidth(),
        type = JaArContainerType.PRIMARY,
        fab = {
            JaArFab(
                entries = WordsContainerFabOptions.entries,
                containerType = JaArContainerType.PRIMARY,
                isScrollingUp = false,
                background = MaterialTheme.colorScheme.background,
                onClickOption = {
                    when (it) {
                        WORD -> actions.onAddNewWord()
                        CATEGORY -> actions.onAddNewCategory()
                        else -> {}
                    }
                }
            )
        },
    ) {
        if (paginatedWords.itemCount > 0) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(minSize = 200.dp),
                modifier = Modifier.fillMaxSize(),
                verticalItemSpacing = 12.dp,
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start)
            ) {
                repeat(paginatedWords.itemCount) {
                    val word = paginatedWords[it] ?: return@repeat
                    word.groupName?.let {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            Text(text = it)
                        }
                    }
                    item(/*key = word.id*/) {
                        WordCard(
                            word = word,
                            isOtherSelectedAndThisNot = (word.wordId !in userInputState.selectedCardsIds)
                                    && userInputState.selectedCardsIds.isNotEmpty()
                                    && userInputState.isSelectionMode,
                            onClick = { actions.onClickCard(word.wordId) },
                            onLongClick = { actions.onLongClickCard(word.wordId) },
                            onDoubleClick = { actions.onDoubleClickCard(word.wordId) },
                            focusedCard = userInputState.focusedCard,
                        )
                    }
                }
            }
        } else { // cards data is empty
            Text(
                modifier = Modifier.fillMaxSize(),
                text = thereNoModel(item = CoreUi.plurals.word),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

