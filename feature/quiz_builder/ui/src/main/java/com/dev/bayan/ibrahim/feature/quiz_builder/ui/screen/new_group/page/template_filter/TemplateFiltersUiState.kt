package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.template_filter

import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

internal data class TemplateFiltersUiState(
    val filters: PersistentList<Filter> = persistentListOf(),
    val selectedFilterId: Long? = null,
    val unAllowedFilterKeys: PersistentSet<Int> = persistentSetOf(),
) {
    val validData = selectedFilterId != null
}
