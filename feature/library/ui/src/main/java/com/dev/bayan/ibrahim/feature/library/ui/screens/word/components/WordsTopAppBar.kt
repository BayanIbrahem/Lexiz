package com.dev.bayan.ibrahim.feature.library.ui.screens.word.components


import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dev.bayan.ibrahim.core.ui.components.action.JaArActionStyle
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.drop_down.JaArIconDropDownMenu
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainer
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.components.topAppBar.JaArTopAppBar
import com.dev.bayan.ibrahim.core.ui.res.actionOnModelResource
import com.dev.bayan.ibrahim.feature.library.domain.model.words.WordBaseDataSet
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.filter_sheet.WordsFilterSheet
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.filter_sheet.WordsFilterSheetEvent
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.filter_sheet.WordsFilterSheetState
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.WordsScreenUserInputState
import com.dev.bayan.ibrahim.feature.library.ui.screens.word.utils.WordsSelectionMenu
import com.dev.bayan.ibrahim.feature.ui.R
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import com.dev.bayan.ibrahim.core.ui.R as UiRes


/**
 * top app bar for content type screen in library feature
 * @param userInputData data for the bar
 * @param onNavigateUp callback function when navigate up button clicked
 * @param onClickDropDownMenuItem callback function when menu button clicked
 */
@Composable
internal fun WordsTopAppBar(
    modifier: Modifier = Modifier,
    isBottomSheet: Boolean,
    userInputData: WordsScreenUserInputState,
    baseDataSet: WordBaseDataSet,
    filterSheetState: WordsFilterSheetState,
    onClickDropDownMenuItem: (WordsSelectionMenu) -> Unit,
    selectionMenuOptionEnability: (WordsSelectionMenu) -> Boolean,
    onCancelSelectionMode: () -> Unit,
    onFilterSheetEvent: (event: WordsFilterSheetEvent) -> Unit,
) {
    JaArContainer(
        modifier = modifier,
        type = JaArContainerType.PRIMARY,
    ) {
        JaArTopAppBar(
            title = JaArDynamicString.PluralRes(UiRes.plurals.word, 11),
            subtitle = null, // fixme
            isSelectionMode = userInputData.isSelectionMode,
            normalActions = {
                ContentNormalModeActions(
                    isBottomSheet = isBottomSheet,
                    baseDataSet = baseDataSet,
                    filterSheetState = filterSheetState,
                    onEvent = onFilterSheetEvent,
                )
            },
            selectionActions = {
                ContentSelectionModeActions(
                    optionEnability = selectionMenuOptionEnability,
                    selectionItemsCount = userInputData.selectedCardsIds.count(),
                    onClickMenuItem = onClickDropDownMenuItem,
                    onCancelSelection = onCancelSelectionMode,
                )
            },
            onNavigateUp = null,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContentNormalModeActions(
    modifier: Modifier = Modifier,
    isBottomSheet: Boolean,
    baseDataSet: WordBaseDataSet,
    filterSheetState: WordsFilterSheetState,
    onEvent: (event: WordsFilterSheetEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    Row(
        modifier = modifier,
    ) {
        IconButton(onClick = { scope.launch { sheetState.show() } }) {
            Icon(
                imageVector = Icons.Filled.FilterList,
                contentDescription = null,
            )
            WordsFilterSheet(
                isBottomSheet = isBottomSheet,
                sheetState = sheetState,
                baseDataSet = baseDataSet,
                dataState = filterSheetState,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun ContentSelectionModeActions(
    modifier: Modifier = Modifier,
    selectionItemsCount: Int,
    optionEnability: (WordsSelectionMenu) -> Boolean,
    onClickMenuItem: (WordsSelectionMenu) -> Unit,
    onCancelSelection: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$selectionItemsCount")
        // fixme
        // todo, add drop down menu
        JaArIconDropDownMenu(
            icon = JaArDynamicVector.Vector(Icons.Filled.MoreVert),
            selected = null,
            options = WordsSelectionMenu.entries.toPersistentList(),
            style = JaArActionStyle.SECONDARY,
            onSelectItem = { it, _ ->
                onClickMenuItem(it)
            },
            optionEnability = optionEnability,
        )
        IconButton(onClick = onCancelSelection) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }
}

