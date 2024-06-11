package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter

import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf

internal data class NewFilterUiState(
    val saveAsNewCopy: Boolean? = null,
    val selectedFilter: Filter? = null,
    val showAll: Boolean = false,
    val firstLanguageTypes: PersistentSet<Long>? = null,
    val firstLanguageCategories: PersistentSet<Long>? = null,
    val unAllowedFilterKeys: PersistentSet<Int> = persistentSetOf(),
) {
    val enableHideExtraItemsFlag = firstLanguageTypes != null && firstLanguageCategories != null
}