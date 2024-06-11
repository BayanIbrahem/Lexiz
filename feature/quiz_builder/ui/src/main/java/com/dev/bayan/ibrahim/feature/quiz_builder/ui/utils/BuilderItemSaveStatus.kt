package com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.BuilderItemSaveStatus
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.BuilderItemSaveStatus.*


internal val BuilderItemSaveStatus.color: Color
    @Composable
    get() = when (this) {
        EDITED -> MaterialTheme.colorScheme.error
        NEW -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

internal fun BuilderItemSaveStatus.updateSaveStatus(
    savingSucceeded: Boolean,
): BuilderItemSaveStatus = when (this) {
    SAVED -> if (savingSucceeded) SAVED else EDITED
    EDITED -> if (savingSucceeded) SAVED else EDITED
    NEW -> if (savingSucceeded) SAVED else NEW
}
