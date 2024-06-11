package com.dev.bayan.ibrahim.feature.library.ui.screens.word

import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentHashSetOf

internal data class WordsScreenUserInputState(
    // selection:
    val isSelectionMode: Boolean = false,
    val selectedCardsIds: PersistentSet<Long> = persistentHashSetOf(),
    val focusedCard: Long? = null,
)