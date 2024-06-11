package com.dev.bayan.ibrahim.core.ui.components.search_bar.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HistoryToggleOff
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.runtime.Immutable
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.components.dynamic.asDynamicVector


@Immutable
data class JaArSearchSuggestion(
    val title: String = "Title",
    val supportingText: String = "Supporting Text",
    val isResultFromQueryHistory: Boolean = true,
    val icon: JaArDynamicVector = if (isResultFromQueryHistory) {
        Icons.Default.HistoryToggleOff.asDynamicVector()
    } else {
        Icons.Default.NorthEast.asDynamicVector()
    },
    val id: Long = -1
)



