package com.dev.bayan.ibrahim.core.ui.components.search_bar


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.search_bar.components.JaArSearchSuggestion
import com.dev.bayan.ibrahim.core.ui.components.search_bar.components.JaArMangaSearchSuggestionsList
import com.dev.bayan.ibrahim.core.ui.theme.JaArTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JaArSearchBar(
    modifier: Modifier = Modifier,
    isLoggedIn: Boolean = false,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    active: Boolean = false,
    onActiveChange: (Boolean) -> Unit = {},
    suggestions: List<JaArSearchSuggestion> = emptyList(),
    onFilterIconButtonClick: () -> Unit = {},
    onProfileIconButtonClick: () -> Unit = {},
    onResultFromQueryHistoryClick: (String) -> Unit = {},
    onResultFromSearchQueryClick: (Long) -> Unit = {}
) {
    DockedSearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        leadingIcon = {
            if (active) {
                IconButton(onClick = { onActiveChange(false) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                        contentDescription = stringResource(id = R.string.search_arrow_back_icon_description),
                        contentDescription = null
                    )
                }
            } else {
                IconButton(onClick = onFilterIconButtonClick) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
//                        contentDescription = stringResource(id = R.string.filter_list_icon_description)
                        contentDescription = null
                    )
                }
            }
        },
        placeholder = {
            Text(
//                text = stringResource(id = R.string.manga_search_bar_placeholder_text),
                text = "place holder",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
                IconButton(onClick = onProfileIconButtonClick) {
                    Icon(
                        // TODO handle icon changes on login
                        imageVector = if (isLoggedIn) Icons.Default.AccountCircle else Icons.Default.AccountCircle,
//                        contentDescription = stringResource(id = R.string.account_icon_description)
                        contentDescription = null,
                    )
                }
            }
        },
        modifier = modifier.fillMaxWidth()
    ) {
        JaArMangaSearchSuggestionsList(
            suggestions = suggestions,
            onResultFromQueryHistoryClick = onResultFromQueryHistoryClick,
            onResultFromSearchQueryClick = onResultFromSearchQueryClick
        )
    }
}