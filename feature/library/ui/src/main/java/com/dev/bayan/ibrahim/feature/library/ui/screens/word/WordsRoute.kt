package com.dev.bayan.ibrahim.feature.library.ui.screens.word


import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.utils.WordsSelectionMenu
import com.dev.bayan.ibrahim.feature.library.ui.theme.JaArLibraryTheme


/**
 * route for content screen
 */
@Composable
internal fun WordsRoute(
    modifier: Modifier = Modifier,
    routeViewModel: WordsScreenViewModel = hiltViewModel(),
    screenSize: JaArScreenSize,
    onNavigateUp: () -> Unit,
    onEditWord: (Long?) -> Unit,
    onEditCategory: (Long?) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        routeViewModel.initWords()
    }
    val actions by remember {
        mutableStateOf(
            WordsScreenActions(
                onNavigateUp = onNavigateUp,
                onDropDownMenuItemClick = {
                    routeViewModel.onDropDownMenuItemSelect(it, onEditWord)
                },
                onClickCard = routeViewModel::onClickCard,
                onLongClickCard = routeViewModel::onLongClickCard,
                onDoubleClickCard = routeViewModel::onDoubleClickCard,
                onCancelSelectionMode = routeViewModel::onCancelSelectionMode,
                onAddNewWord = { onEditWord(null) },
                onAddNewCategory = { onEditCategory(null) },
                onFilterSheetEvent = routeViewModel::onFilterSheetEvent,
                selectionMenuOptionEnability = routeViewModel::selectionMenuOptionEnability,
            )
        )
    }
    val userInputState by routeViewModel.userInputState.collectAsStateWithLifecycle()
    val filterSheetState by routeViewModel.filterSheetState.collectAsStateWithLifecycle()
    val baseDataSet by routeViewModel.wordBaseDataSet.collectAsStateWithLifecycle()
    val paginatedWords = routeViewModel.wordsFlow.collectAsLazyPagingItems()
    BackHandler(onBack = onNavigateUp)
//   JaArLibraryTheme {
    WordsScreen(
        modifier = modifier,
        actions = actions,
        userInputState = userInputState,
        filterSheetState = filterSheetState,
        baseDataSet = baseDataSet,
        paginatedWords = paginatedWords,
        screenSize = screenSize,
    )
//   }
}
