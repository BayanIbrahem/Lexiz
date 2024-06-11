package com.dev.bayan.ibrahim.core.ui.components.search_bar.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun JaArSearchSuggestionsListItem(
    modifier: Modifier = Modifier,
    fillMaxWidthPaddingModifier: Modifier = Modifier,
    smallPaddingModifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp),
    jaArSearchSuggestion: JaArSearchSuggestion = JaArSearchSuggestion(),
    onResultFromQueryHistoryClick: (String) -> Unit = {},
    onResultFromSearchQueryClick: (Long) -> Unit = {}
) {
    Box(
        modifier = modifier
            .clickable(enabled = true) {
                if (jaArSearchSuggestion.isResultFromQueryHistory) {
                    onResultFromQueryHistoryClick(jaArSearchSuggestion.title)
                } else {
                    onResultFromSearchQueryClick(jaArSearchSuggestion.id)
                }
            }
    ) {
        Row(
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = Alignment.CenterVertically,
            modifier = fillMaxWidthPaddingModifier
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = jaArSearchSuggestion.icon.vector,
                    contentDescription = null,
                    modifier = smallPaddingModifier
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = jaArSearchSuggestion.title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = jaArSearchSuggestion.supportingText,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
        }
    }
}