package com.dev.bayan.ibrahim.core.ui.components.search_bar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun JaArMangaSearchSuggestionsList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    suggestions: List<JaArSearchSuggestion> = emptyList(),
    onResultFromQueryHistoryClick: (String) -> Unit = {},
    onResultFromSearchQueryClick: (Long) -> Unit = {}
) {
    val fillMaxWidthPaddingModifier = remember {
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    }
    val smallPaddingModifier = remember { Modifier.padding(8.dp) }
    val clipClickableModifier = remember {
        Modifier
            .clip(CircleShape)
            .semantics(mergeDescendants = true) { }
    }
    val horizontalArrangement = remember { Arrangement.spacedBy(16.dp) }

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
        modifier = modifier
    ) {
        items(
            items = suggestions,
            key = { suggestion -> suggestion.id }
        ) { suggestion ->
            JaArSearchSuggestionsListItem(
                jaArSearchSuggestion = suggestion,
                onResultFromQueryHistoryClick = onResultFromQueryHistoryClick,
                onResultFromSearchQueryClick = onResultFromSearchQueryClick,
                horizontalArrangement = horizontalArrangement,
                fillMaxWidthPaddingModifier = fillMaxWidthPaddingModifier,
                smallPaddingModifier = smallPaddingModifier,
                modifier = clipClickableModifier
            )
        }
    }
}